package com.napier.sem;

/** Menu Class - Prints the menu that the user interacts with
 * @author Stuart C. Alexander
 * @since Oct 2025
 */
public class Menu {

    /// Class wide variables;
    IO input = new IO();
    Controller cont;

    ///  Constructor;
    public Menu(Controller controller) {
        cont = controller;
        System.out.println("""
                      |-----------------------------------------------------------------------------------------------------------/
                      |----- POPULATION REPORTS -------- v1.0 -------------------------------------------------------------------/
                      |---------------------------------------------------------------------------------------------------------/
                      | - Coded by : -- Stuart Alexander, Socrates Davidopoulos, Alan Glowacz & Dominic Benell ----------------/
                      |-------------------------------------------------------------------------------------------------------/
                      """);
    }

    /** Prints the main menu - triggered in the constructor
     * @author Stuart C. Alexander
     * @since Oct 2025
     */
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


        while(running) {

            System.out.print("--> Please enter a selection: ");
            int choice = input.getInteger();

            switch(choice) {
                case 1: // Print Population Reports
                    cont.populationReports();
                    break;
                case 2: // Print City Reports
                    int n = cont.getN();
                    cont.outputCityReports(n);
                    break;
                case 3: // Print Capital Reports
                    int X = input.getN();
                    cont.capitalReports(X);
                    break;
                case 4: // Print Country Reports
                    int N = input.getN();
                    cont.countryReports(N);
                    break;
                case 5: // Print Language Reports
                    cont.languageReports();
                    break;
                case 6: // Exit Application
                    running = false;
                    System.out.println("""
                            |------------------------------------------------------------------------------------------------------|
                            |------------------------------------------ GOODBYE! --------------------------------------------------|
                            |------------------------------------------------------------------------------------------------------|
                            """);
                    break;
                default: // default (1-6 not selected)
                    System.out.println("Invalid Selection!");
            }
        }
    }
}