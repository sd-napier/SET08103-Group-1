// package identifier
package com.napier.sem;
// imports the entire java.sql library
import java.sql.*;

//-------------------------------------------------------//
//- Wednesday 9AM Group 1 - SEM TR1 Assessment          //
//-----------------------------------------------------//
//- Coded by Stuart, Alan, Dominic and Socrates       //
//---------------------------------------------------//
//- OCT 2025 ---------------------------------------//
//-------------------------------------------------//

// This class in its current state is the starting point for our application;
// It tests connection to our database and handles checked exceptions, should they occur.
public class App
{
    public static void main(String[] args)
    {
        // Try catch block to gracefully catch an exception, should it occur
        try
        {
            // Load jdbc database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        // Should class not found exception occur...
        catch (ClassNotFoundException e)
        {
            // ... prints subsequent message and quits application
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // define connection to the database
        Connection con = null;
        int retries = 100;  // max number of attempts to connect to the database
        for (int i = 0; i < retries; ++i) // for loop to iterate through attempts
        {
            // prints below message upon every attempt
            System.out.println("Connecting to database...");
            try
            {
                // Waits a 1 second ...
                Thread.sleep(1000);
                // then attempts to connect to database...
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
                System.out.println("Successfully connected");
                // waits another second...
                Thread.sleep(1000);
                // ... and breaks out of for loop if successful
                break;
            }
            // if the above try block is not successful...
            catch (SQLException sqle)
            {
                // Prints the below message if connection failed
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                // Prints below message if the thread was interrupted
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

        // If the connection was successful...
        if (con != null)
        {
            try
            {
                // ... Closes connection
                con.close();
            }
            catch (Exception e)
            {
                // or prints this error if it fails to close connection
                System.out.println("Error closing connection to database");
            }
        }
    }
}