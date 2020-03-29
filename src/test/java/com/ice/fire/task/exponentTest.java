package com.ice.fire.task;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
//was helping out a friend figure something out.
// Part of his task is to print a 'Yes' message when the user enters a  number that's a power of 10.
//like 10, 100, 1000, 10000, ..., etc
public class exponentTest {
    private Double result;
    @Before
    public void createInstance(){
        Calculator calculator = new Calculator();
        result = calculator.isPowerOfTen(10000000000000000L);
        //assertEquals(3.0, result, 0);
    }

    @Test
    public void isPowerOfTenTestShouldReturnTrue(){
        Calculator calculator = new Calculator();
        boolean isWholeNumber  = calculator.isInteger(result);
        assertTrue(isWholeNumber);
    }

    @Test
    public void isPowerOfTenTestShouldPrintResult(){
        Calculator calculator = new Calculator();
        String output = calculator.showResult(result);
        System.out.println(output);
        assertEquals("Yes", output);
    }
}
