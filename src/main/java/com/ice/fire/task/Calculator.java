package com.ice.fire.task;

public class Calculator {
    public Double isPowerOfTen(long input) {
        Double result = Math.log10(input);
        return result;
    }

    public boolean isInteger(Double result) {
        return result == result.intValue();
    }

    public String showResult(Double result) {
        return (isInteger(result)) ? "Yes" : "No";
    }
}
