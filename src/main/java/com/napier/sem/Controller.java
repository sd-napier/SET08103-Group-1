package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stual
 * @date 29/10/2025
 */
public class Controller {

    public Controller() {

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


    public void testQuery() throws SQLException {
        String testQuery = "SELECT DISTINCT Continent FROM country;";
        String category  = "Continent";

        runTestQuery(testQuery, category);

    }
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
