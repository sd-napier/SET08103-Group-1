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
    private IO input;
    private Controller cont;
//    private Queries queries;
    private PopulationReports popReports;
    private LanguageReports langReports;
    private CountryReports coReports;
    private Connection conn;

    /** Constructor - Creates a new instance of queries, assigns itself(this) as the class wide wariable 'cont'
     * ... to pass to other classes, and creates a new instance of the population reports class.
     * @author Stuart C.Alexander
     * @since Nov 2025
     */
    public Controller() {
        cont = this;
        input = new IO();
        //queries = new Queries();
        popReports = new PopulationReports(cont);
        langReports = new LanguageReports(cont);
        coReports = new CountryReports(cont);

    }

//    /** runQuery Method - runs a passed in query(String query),
//     * and prints the results from a defined column(String category)
//     * @author Stuart C. Alexander
//     * @since Oct 2025
//     * @param query
//     * @param category
//     */
//    public void runTestQuery(String query, String category) {
//        ResultSet result = null;
//        try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
//            PreparedStatement stmt = conn.prepareStatement(query);
//            result = stmt.executeQuery();
//            while (result.next()) {
//                System.out.println(result.getString(category));
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage() + "RUN TEST QUERY FAILED!");
//            e.printStackTrace();
//        }
//
//    }

//    /** testQuery - This method contains a query that selects each of the continents contained in the database, and passes it to run test query.
//     * @author Stuart C. Alexander
//     * @since Oct 2025
//     */
//    public void testQuery() {
//        String testQuery = "SELECT DISTINCT Continent FROM country;";
//        String category  = "Continent";
//
//        runTestQuery(testQuery, category);
//
//    }
    public ResultSet runQuery(String query) {
        ResultSet result = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Failed to Run Query from Docker deployed App!");
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet runQueryLocal(String query) {
        ResultSet result = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Failed to Run Query from IntelliJ(Local) App!");
            e.printStackTrace();
        }
        return result;
    }

    /** Assembles the population reports and sends them to printer method
     * @author Stuart C. Alexander
     * @since Nov 2025
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
        System.out.println("PRINTING...");
        /// sends data and filename to the .md printer
        printToFile(popReports.getFilename(), output );
    }

    /** Assembles the language reports and sends them to printer method
     * @author Stuart C. Alexander
     * @since Nov 2025
     */
    public void languageReports() {
        StringBuilder output = new StringBuilder();
        output.append("Language Reports\r\n");
        output.append(langReports.getHeadingFormat());
        output.append("| --- | --- | --- |\r\n");

        ArrayList<String> languages = langReports.getLanguageReport();
        for (String language : languages) {
            output.append(language);
        }
        System.out.println("PRINTING...");
        printToFile(langReports.getFilename(), output );
    }

    /** Assembles the country reports and sends them to the printer
     * @author Stuart C. Alexander
     * @since Nov 2025
     * @param limit - passed in value for user defined N
     */
    public void countryReports(int limit) {

        StringBuilder output = new StringBuilder();
        output.append("Country Reports\r\n");
        output.append("| --- | --- | --- | --- | --- | --- |\r\n");
        output.append("(World)\r\n");
        output.append("| --- | --- | --- | --- | --- | --- |\r\n");
        output.append(coReports.getHeadingFormat());

        /// All the countries in the world organised by largest population to smallest.
        ArrayList<String> world = coReports.getCountryReportWorld(limit);
        for (String country : world) {
            output.append(country);
        }

        /// All the countries in a continent organised by largest population to smallest.
        output.append("| --- | --- | --- | --- | --- | --- |\r\n");
        output.append("(By Continent)\r\n");
        ArrayList<String> continents = getContinentNames();

        for (String continent : continents) {
            output.append("| --- | --- | --- | --- | --- | --- |\r\n");
            output.append(continent.toUpperCase() + "\r\n");
            output.append("| --- | --- | --- | --- | --- | --- |\r\n");
            output.append(coReports.getHeadingFormat());

            ArrayList<String> byContinent = coReports.getCountryReportContinent(continent, limit);

            output.append(byContinent);
        }

        /// All the countries in a region organised by largest population to smallest.
        output.append("| --- | --- | --- | --- | --- | --- |\r\n");
        output.append("(By Region)\r\n");
        ArrayList<String> regions = getRegionNames();

        for (String region : regions) {
            output.append("| --- | --- | --- | --- | --- | --- |\r\n");
            output.append(region.toUpperCase() + "\r\n");
            output.append("| --- | --- | --- | --- | --- | --- |\r\n");
            output.append(coReports.getHeadingFormat());

            ArrayList<String> byRegion = coReports.getCountryReportRegion(region, limit);

            output.append(byRegion);
        }
        System.out.println("PRINTING...");
        printToFile(coReports.getFilename(), output);
    }











    /**
     * Generates and outputs various city reports to a Markdown file.
     * <p>
     * The report includes:
     * <ul>
     *     <li>Top N cities in the world</li>
     *     <li>Top N cities per continent</li>
     *     <li>Top N cities per region</li>
     *     <li>Top N cities per country (limited to top 32 countries)</li>
     *     <li>Top N cities per district (limited to top 32 districts)</li>
     * </ul>
     * The results are formatted in Markdown and written to the ./reports directory.
     *
     * @param n the number of cities to include per group (defaults to 32 if n ≤ 0)
     * @author Alan Glowacz
     */
    public void outputCityReports(int n) {
        int N = (n <= 0) ? 32 : n; // Default value if invalid input

        try {
            CityReportService svc = new CityReportService(conn);

            // Markdown table separators for grouped and ungrouped reports
            final String SEP_NO_GROUP   = "| --- | --- | --- | --- |\r\n";
            final String SEP_WITH_GROUP = "| --- | --- | --- | --- | --- |\r\n";

            StringBuilder out = new StringBuilder();
            out.append("City Reports\r\n\r\n");

            // World, Top N cities overall
            out.append("World — Top ").append(N).append("\r\n");
            out.append(CityPrinter.HEADING_NO_GROUP).append(SEP_NO_GROUP);
            for (String line : CityPrinter.toMarkdownRows(svc.worldCities(N), false)) {
                if (line.equals(CityPrinter.HEADING_NO_GROUP)) continue;
                out.append(line);
            }
            out.append("\r\n");

            // Per Continent, Top N cities per continent
            out.append("Per Continent — Top ").append(N).append(" per continent\r\n");
            out.append(CityPrinter.HEADING_WITH_GROUP).append(SEP_WITH_GROUP);
            for (String line : CityPrinter.toMarkdownRows(svc.continentCities(N), true)) {
                if (line.equals(CityPrinter.HEADING_WITH_GROUP)) continue;
                out.append(line);
            }
            out.append("\r\n");

            // Per Region, Top N cities per region
            out.append("Per Region — Top ").append(N).append(" per region\r\n");
            out.append(CityPrinter.HEADING_WITH_GROUP).append(SEP_WITH_GROUP);
            for (String line : CityPrinter.toMarkdownRows(svc.regionCities(N), true)) {
                if (line.equals(CityPrinter.HEADING_WITH_GROUP)) continue;
                out.append(line);
            }
            out.append("\r\n");

            // Per Country, Top 32 countries, Top N cities each
            out.append("Per Country — Top 32 countries, Top ").append(N).append(" cities each\r\n");
            out.append(CityPrinter.HEADING_WITH_GROUP).append(SEP_WITH_GROUP);
            for (String line : CityPrinter.toMarkdownRows(svc.countryCities(32, N), true)) {
                if (line.equals(CityPrinter.HEADING_WITH_GROUP)) continue;
                out.append(line);
            }
            out.append("\r\n");

            // Per District, Top 32 districts, Top N cities each
            out.append("Per District — Top 32 districts, Top ").append(N).append(" cities each\r\n");
            out.append(CityPrinter.HEADING_WITH_GROUP).append(SEP_WITH_GROUP);
            for (String line : CityPrinter.toMarkdownRows(svc.districtCities(32, N), true)) {
                if (line.equals(CityPrinter.HEADING_WITH_GROUP)) continue;
                out.append(line);
            }
            out.append("\r\n");

            // Write all report data to file in the ./reports directory
            printToFile("cityReports.md", out);
            System.out.println("✓ City reports written to ./reports/cityReports.md");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("City report generation failed.");
        }
    }

    /**
     * small function to receive input, parse into integer and apply default if necessary
     * @return n = user input
     */
    public int getN() {
        System.out.print("→ Please enter a number (press Enter for default 32): ");
        int n = input.getInteger();   // returns 0 if blank/invalid
        if (n <= 0) n = 32;           // default to 32
        return n;
    }

//    /** Method to write all the Capital Reports
//     *
//     */
//    public void outputCapitalReports() {
//        String filename = "capitalReports.md";
//        String headings = "";
//    }
//
//    /** Method to write all the Country reports
//     *
//     */
//    public void outputCountryReports() {
//        String filename = "countryReports.md";
//        String headings = "";
//    }
//
//
//

    public ArrayList<String> getContinentNames() {
        ArrayList<String> continents = new ArrayList<>();
        try {
            /// Result set to store query results (CHANGE FROM .runQueryLocal TO .runQuery AFTER TESTING)
            ResultSet allContinents = runQueryLocal("SELECT DISTINCT Continent FROM country");
            while (allContinents.next()) {
                continents.add(allContinents.getString("Continent"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nFAILED TO GET ALL CONTINENTS IN CONTROLLER METHOD");
        }
        return continents;
    }

    public ArrayList<String> getRegionNames() {
        ArrayList<String> regions = new ArrayList<>();
        try {
            /// Result set to store query results (CHANGE FROM .runQueryLocal TO .runQuery AFTER TESTING)
            ResultSet allRegions = runQueryLocal("SELECT DISTINCT Region FROM country");
            while (allRegions.next()) {
                regions.add(allRegions.getString("Region"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nFAILED TO GET ALL REGIONS IN CONTROLLER METHOD");
        }
        return regions;
    }



    /** Print to .md file
     * @author Stuart C. Alexander
     * @since Nov 2025
     * @param filename the filename to be written to
     * @param data the assembled report stringbuilder to be printed into .md
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