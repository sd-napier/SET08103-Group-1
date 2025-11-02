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

    public void closeScanner() {
        input.close();
    }
}
