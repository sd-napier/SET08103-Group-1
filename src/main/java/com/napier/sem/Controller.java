package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** A Controller Method for Interacting with the database, and running queries
 * @author Stuart C.Alexander
 * @since Oct 2025
 */
public class Controller {

    public Controller() {
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

    /** testConnection - This Method tests the database connection, and if successful runs the test query which prints the names of all the Continents contained in the Database.
     *
     */
    public void testConnection() {
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
