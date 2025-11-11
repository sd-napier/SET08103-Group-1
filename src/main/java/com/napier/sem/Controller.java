package com.napier.sem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/** A Controller Class for interacting with the database container
 * @author Stuart C.Alexander
 * @since Oct 2025
 */
public class Controller {

    /// Class wide variables;
    Controller cont;
    Queries queries;
    PopulationReports popReports;
    Connection conn;

    /** Constructor - Creates a new instance of queries, assigns itself(this) as the class wide wariable 'cont'
     * ... to pass to other classes, and creates a new instance of the population reports class.
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
            PreparedStatement stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** Assembles the population reports
     *
     */
    public void populationReports() {
        StringBuilder output = new StringBuilder();
        output.append("Population Reports\r\n");
        output.append(popReports.getHeadings());
        output.append("| --- | --- | --- | --- | --- | --- |\r\n");
        output.append(popReports.getWorldPopulation());

        output.append("| --- | --- | --- | --- | --- | --- |\r\n");
        output.append("Continents\r\n");
        output.append("| --- | --- | --- | --- | --- | --- |\r\n");

        ArrayList<String> continents = popReports.getPopulation("Continent");
        for (String continent : continents) {
            output.append(continent);
        }

        output.append("| --- | --- | --- | --- | --- | --- |\r\n");
        output.append("Regions\r\n");
        output.append("| --- | --- | --- | --- | --- | --- |\r\n");

        ArrayList<String> regions = popReports.getPopulation("Region");
        for (String region : regions) {
            output.append(region);
        }

        output.append("| --- | --- | --- | --- | --- | --- |\r\n");
        output.append("Countries\r\n");
        output.append("| --- | --- | --- | --- | --- | --- |\r\n");

        ArrayList<String> countries = popReports.getPopulation("Name");
        for (String country : countries) {
            output.append(country);
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
                conn = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
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
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33060/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
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