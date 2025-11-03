package com.napier.sem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;

/** A Controller Method for Interacting with the database, and running queries
 * @author Stuart C.Alexander
 * @since Oct 2025
 */
public class Controller {

    /** Constructor
     * @author Stuart C.Alexander
     * @since Nov 2025
     */
    public Controller() {
        // Create Instance of the Queries Class
        Queries q = new Queries();
    }

    /** runQuery Method - runs a passed in query(String query), and prints the results from a defined column(String category)
     * @author Stuart C. Alexander
     * @since Oct 2025
     * @param query
     * @param category
     * @throws SQLException
     */
    public void runTestQuery(String query, String category) throws SQLException {
            ResultSet result = null;
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
                PreparedStatement stmt = conn.prepareStatement(query);
                result = stmt.executeQuery();
                while (result.next()) {
                    System.out.println(result.getString(category));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
    }

    /** testQuery - This method contains a query that selects each of the continents contained in the database, and passes it to run test query.
     * @author Stuart C. Alexander
     * @since Oct 2025
     * @throws SQLException
     */
    public void testQuery() throws SQLException {
        String testQuery = "SELECT DISTINCT Continent FROM country;";
        String category  = "Continent";

        runTestQuery(testQuery, category);

    }
    public ResultSet runQuery(String query) throws SQLException {
        ResultSet result = null;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
            PreparedStatement stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void populationReports() {
        // Result set for storing query results
        ResultSet data;

        //World Population Query (needs table appended)
        String worldPopQuery = "SELECT SUM(Population) as World_pop FROM ";

        // Count variables for storing population counts
        BigInteger cityCount = null;
        BigInteger popCount = null;

        try {
            data = runQuery(worldPopQuery + "country;"); // Run query on the 'country' table
            while (data.next()) {
                popCount = BigInteger.valueOf(data.getLong("World_pop")); // Add total pops
            }

            data = runQuery(worldPopQuery + "city;");   // Run query on the 'city' table

            while (data.next()) {
                cityCount = BigInteger.valueOf(data.getLong("World_pop")); // add total city dwellers
            }
            // little cheat here, working with big integers is hard to do math/ work out percentages so...
            BigInteger newPopCount = popCount.divide(BigInteger.valueOf(100));
            BigInteger newCityCount = cityCount.divide(BigInteger.valueOf(100));

            float perPopCount = newPopCount.floatValue();
            float perCityCount = newCityCount.floatValue();

            float perCity = ((perCityCount / perPopCount) * 100);

            System.out.println("The population of the world is " + popCount + ". " + cityCount + "(" + perCity + "%) live in cities," + (popCount.subtract(cityCount)) + "(" + (100 - perCity) + "%) do not.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Query Failed");
        }
//        System.out.println("|---------------------------------------------------------------------------------------------------|");
//
//        String row = String.format("|%-10s|%-15s|%-23s|%-13s|");

    }

    /** Method to write population reports
     *
     */
    public void outputPopulationReports() {
        String filename = "popReports.md";
        String headings = "|   Name   |  Population  |   Total in Cities   | % in Cities |   Not in cities   | % not in cities |";

    }

    /** Method to write all the City Reports
     *
     */
    public void outputCityReports() {
        String filename = "cityReports.md";
        String headings = "";
    }

    /** Method to write all the Capital Reports
     *
     */
    public void outputCapitalReports() {
        String filename = "capitalReports.md";
        String headings = "";
    }

    /** Method to write all the Country reports
     *
     */
    public void outputCountryReports() {
        String filename = "countryReports.md";
        String headings = "";
    }

    /** Method to write all the language reports
     *
     */
    public void outputLanguageReports() {
        String filename = "languageReports.md";
        String headings = "";
    }

    /** Print to .md file
     *
     */
    public void printTofile(String filename, String headings, ResultSet data) {

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("----------------------------------------------------------------------------------------\n");
        sb.append(headings + "\n");
        sb.append("----------------------------------------------------------------------------------------\n");

        // Code block to append data to string builder
        //
         //

        try {
            new File("./reports/").mkdir();
            BufferedWriter wr = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            wr.write(sb.toString());
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /** testConnection - This Method tests the database connection upon creation from the Docker container, and if successful runs the
     * test query which prints the names of all the Continents contained in the Database.
     *
     */
    public void dockerTestConnection() {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Connection to the database
        Connection con = null;
        int retries = 100;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(1000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
                System.out.println("Successfully connected");
                // Wait a bit
                Thread.sleep(1000);
                testQuery();
                Menu menu = new Menu(this);
                menu.printMainMenu();
                // Exit for loop
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public void LocalTestConnection() {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Connection to the database
        Connection con = null;
        int retries = 100;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(1000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33060/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
                System.out.println("Successfully connected");
                // Wait a bit
                Thread.sleep(1000);
                Menu menu = new Menu(this);
                menu.printMainMenu();
                // Exit for loop
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
}
