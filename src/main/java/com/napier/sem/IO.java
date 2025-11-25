package com.napier.sem;

import java.util.Scanner;

/**
 * @author stual
 * @date 02/11/2025
 */
public class IO {

    public IO() {

    }
    // Instance of the scanner
    private final Scanner input = new Scanner(System.in);

    // Returns instance of the scanner for use in other classes
    public Scanner getInput() {
        return input;
    }

    //This method takes input and parses it to an integer, if no number was entered then returns a '0'
    public int getInteger() {

        String userInput = getInput().nextLine();
        int result;

        try {
            result = Integer.parseInt(userInput);
        }
        catch (NumberFormatException e){
            result = 0 ;
        }
        return result;

    }

    public int getN() {
        System.out.print("--> Please enter results limit: ");
        int limit = getInteger();
        if (limit > 32) {
            limit = 32;
            System.out.println("Limit set to maximum(32).");
        } else if (limit == 0) {
            limit = 32;
            System.out.println("No limit selected, or invalid character entered... Setting limit to 32.");
        } else {
            System.out.println("Limit set to " + limit + ".");
        }
        return limit;
    }

    public void closeScanner() {
        input.close();
    }
}
