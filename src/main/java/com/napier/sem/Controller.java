package com.napier.sem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

/** A Controller Method for Interacting with the database, and running queries
 * @author Stuart C.Alexander
 * @since Oct 2025
 */
public class Controller {

    Controller cont;
    Queries queries;
    PopulationReports popReports;

    /** Constructor
     * @author Stuart C.Alexander
     * @since Nov 2025
     */
    public Controller() {
        cont = this;
        queries = new Queries();
        popReports = new PopulationReports(cont);
    }

    /** runQuery Method - runs a passed in query(String query), and prints the results from a defined column(String category)
     * @author Stuart C. Alexander
     * @since Oct 2025
     * @param query
     * @param category
     * @throws SQLException
     */
//    public void runTestQuery(String query, String category) throws SQLException {
//            ResultSet result = null;
//            try {
//                Connection conn = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
//                PreparedStatement stmt = conn.prepareStatement(query);
//                result = stmt.executeQuery();
//                while (result.next()) {
//                    System.out.println(result.getString(category));
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                throw e;
//            }
//    }

    /** testQuery - This method contains a query that selects each of the continents contained in the database, and passes it to run test query.
     * @author Stuart C. Alexander
     * @since Oct 2025
     * @throws SQLException
     */
//    public void testQuery() throws SQLException {
//        String testQuery = "SELECT DISTINCT Continent FROM country;";
//        String category  = "Continent";
//
//        runTestQuery(testQuery, category);
//
//    }
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

    public ResultSet runQueryLocal(String query) throws SQLException {
        ResultSet result = null;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33060/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
            PreparedStatement stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void populationReports() {
        StringBuilder output = new StringBuilder();
        output.append("| --- | --- | --- | --- | --- | --- |\r\n");
        output.append(popReports.getHeadings());
        output.append("| --- | --- | --- | --- | --- | --- |\r\n");
        output.append(popReports.getWorldPopulation());

        ArrayList<String> continents = popReports.getContinentPopulation();
        for (String continent : continents) {
            output.append(continent);
        }

        printToFile(popReports.getFilename(), output );
    }

    /** Method to write population reports
     *
     */
    public void outputPopulationReports() {


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
    public void printToFile(String filename, StringBuilder data) {

        try {
            new File("./reports/").mkdir();
            BufferedWriter wr = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            wr.write(data.toString());
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
//                testQuery();
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
