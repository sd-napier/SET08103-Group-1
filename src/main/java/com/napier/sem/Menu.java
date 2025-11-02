package com.napier.sem;

/**
 * @author stual
 * @date 02/11/2025
 */
public class Menu {

    IO input = new IO();
    Controller cont;

    public Menu(Controller controller) {
        cont = controller;
        System.out.println("""
                |---------------------------------------------------------------------------------------------------------/
                |----- POPULATION REPORTS -------- v1.0 -----------------------------------------------------------------/
                |-------------------------------------------------------------------------------------------------------/
                | - Coded by : -- Stuart Alexander, Socrates Davidopoulos, Alan Glowacz & Dominic Benell --------------/
                |-----------------------------------------------------------------------------------------------------/
                """);
    }

    public void welcomeMessage() {
    }

    public void printMainMenu() {

        boolean running = true;

        System.out.println("""
                |------------------------------------------------------------------------------------------------------|
                |----------------------------------------- MAIN MENU --------------------------------------------------|
                |------------------------------------------------------------------------------------------------------|
                - 1 - Print Population Reports
                - 2 - Print City Reports
                - 3 - Print Capital Reports
                - 4 - Print Country Reports
                - 5 - Print Language Reports
                - 6 - Exit Application""");
        System.out.print("--> Please enter a selection: ");

        while(running) {

            int choice = Integer.parseInt(input.getInput().nextLine());

            switch(choice) {
                case 1: // Print Population Reports
                    cont.populationReports();
                    break; // break out of while loop
                case 2:
                    System.out.println("Test City Reports");
                    break; // break out of while loo
                case 3:
                    System.out.println("Test Capital Reports");
                    break; // break out of while loop
                case 4:
                    System.out.println("Test Country Reports");
                    break; // break out of while loop
                case 5:
                    System.out.println("Test Language Reports");
                    break; // break out of while loop
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid Selection! --> Please enter a selection: ");
            }
        }
    }
}
