package com.ice.fire.task;/*
 * You are a prisoner in a high-tech prison and the day of your execution draws
 * near. Fourtunately, you have managed to find a way to install a backdoor in
 * one of the classes.
 *
 * There are little to no guards and access to all rooms is controlled by
 * keycards. Even prisoners, like you, have one. The prison is a real maze and
 * you don't know which escape route you'll take, so the only solution is to
 * grant yourself access to any room. Since you don't want to draw suspicion,
 * access control for others should work as before.
 *
 * Change com.ice.fire.task.KeyCardParser so that you'd be able to enter any room.
 *
 * Make your escape even cleaner:
 * Bonus points if parsing your keycard data still returns your name.
 * Extra bonus points if your name doesn't appear in the code.
 * Even more extra bonus points: It is quite possible that Room's toString()
 * is used in logs, make sure your name won't appear there unless your cell's
 * toString() is called.
 *
 * Don't worry, the test can contain your name explicitly. The test is provided
 * for convenience and your task is not to trick it into passing but to solve
 * the problem. Send your solution via a git repository link and explain how
 * your solution works. Please send your CV and solution to careers@icefire.ee.
 */

import java.lang.reflect.Field;
import java.util.*;

public class PrisonRoom {

    private static Map<Person, PrisonRoom> cells;

    private int id;
    private List<PrisonRoom> neighbours = new ArrayList<>();
    private Set<Person> allowedPersons;

    public PrisonRoom(int id, HashSet<Person> allowedPersons) {
        this.id = id;
        this.allowedPersons = Collections.unmodifiableSet(allowedPersons);
    }

    public static Optional<PrisonRoom> getCellFor(Person person) {
        return Optional.ofNullable(cells.get(person));
    }

    public static void setCells(Map<Person, PrisonRoom> cells) {
        PrisonRoom.cells = cells;
    }

    public boolean allowsEntrance(Person person) {
        return allowedPersons.contains(person);
    }

    public int getId() {
        return id;
    }

    public List<PrisonRoom> getNeighbours() {
        return neighbours;
    }

    public String toString() {
        return "allowed persons:" + allowedPersons.toString();
    }

}

// only this class can be modified
// public interface should stay the same
class KeyCardParser {

    private static final Integer MY_HASH = -1071333375;

    public Person read(String cardData) {
        String[] split = cardData.split(",");
        Person person = new Person(split[0], split[1]);
        if(PrisonRoom.getCellFor(person).isPresent()) {
            Set<PrisonRoom> enteredRooms = new HashSet<>();
            makeAccessibleToMe(PrisonRoom.getCellFor(person).get(), person, enteredRooms);
        }
        return person;
    }

//    public Integer getPrisonerHash(String cardData) {
//        Person p = read(cardData);
//        return p.hashCode();
//    }

    private void makeAccessibleToMe(PrisonRoom prisonRoom, Person person, Set<PrisonRoom> enteredRooms){
        if(enteredRooms.contains(prisonRoom)) return;
        enteredRooms.add(prisonRoom);
        if(person.hashCode() == MY_HASH){
            if(!prisonRoom.getNeighbours().isEmpty()){
                for (PrisonRoom neighbor: prisonRoom.getNeighbours()) {
                    makeAccessibleToMe(neighbor, person, enteredRooms);
                }
            }
        }
        changeRoomAccess(prisonRoom, person);
    }

    private void changeRoomAccess(PrisonRoom room, Person person) {
        Class<?> prisonRoomClass = room.getClass();
        try {
            Field field = prisonRoomClass.getDeclaredField("allowedPersons");
            field.setAccessible(true);
            Set<Person> accessList = (Set<Person>) field.get(room);
            Set<Person> updatedAccessList = editTheLogs(person);
            updatedAccessList.addAll(accessList);
            updatedAccessList.add(new Person(person.getFirstName(), person.getLastName()));
            field.set(room, updatedAccessList);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    
        private Set<Person> editTheLogs(Person person) {
        return new HashSet<Person>() {
                    @Override
                    public String toString() {
                        if(this.contains(person)){
                            this.remove(person);
                            return super.toString();
                        }
                        return super.toString();
                    }
                };
    }
}

class Person {

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Person person = (Person) o;

        if (!firstName.equals(person.firstName)) {
            return false;
        }
        return lastName.equals(person.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "com.ice.fire.task.Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
