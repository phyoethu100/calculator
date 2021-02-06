package edu.csc413.calculator.evaluator;

/**
 * Operand class used to represent an operand
 * in a valid mathematical expression.
 */
public class Operand {

    private int operand;
    /**
     * construct operand from string token.
     */
    public Operand(String token) {
        operand = Integer.parseInt(token);
    }

    /**
     * construct operand from integer
     */
    public Operand(int value) {
        operand = value;
    }

    /**
     * return value of operand
     */
    public int getValue() {
        return operand;
    }

    /**
     * Check to see if given token is a valid
     * operand.
     */
    public static boolean check(String token) {
        try {
            Integer.parseInt(token);
            return true; // return true if the token is an integer
        } catch(NumberFormatException ex) {
            return false;
        }
    }

}