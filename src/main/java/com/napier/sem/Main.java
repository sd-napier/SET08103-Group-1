package com.napier.sem;

// Import java classes used to talk to the database.
import java.sql.*;

/**
 * Entry point for the application.
 * Connects to the MySQL datase and prints population info.
 * @author Socrates Davidopoulos
 * @author dominicbenell
 */
public class Main {
    public static void main(String[] args) {
        // Connection details
        String url = "jdbc:mysql://localhost:3306/SET08103?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String username = "WedTeam1";
        String password = "reallySecurePassword!";

        // placeholder for parameter, set later with stmt.setString
        String sql = "SELECT Population FROM country WHERE Name = ?";

        // Opens connection and prepares SQL query.
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set name of country to United Kingdom.
            stmt.setString(1, "United Kingdom");
            ResultSet rs = stmt.executeQuery();     // Executes query and stores in rs.

            // If result at least one row.
            if (rs.next()) {
                long population = rs.getLong("population");
                System.out.println("The population of the UK is " + population);
            } else {
                // Query returned zero rows.
                System.out.println("No population data found for the United Kingdom\".");
            }
        // Connection to database failed.
        } catch(SQLException e){
            System.out.println("Database connection failed: "+ e.getMessage());

        } catch (Exception e) {
            // If error is something like wrong column name or null pointer.
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
