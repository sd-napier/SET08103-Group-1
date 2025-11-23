package com.napier.sem.testing;

import com.napier.sem.Controller;
import com.napier.sem.PopulationReports;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.File;


import static org.junit.jupiter.api.Assertions.*;

// To run this file right click on this file, edit configurations.
// click the + sign and choose JUnit
// Set the name to AppIntegrationTest
// Set Test kind to class
// Set Class to com.napier.sem.testing.AppIntegrationTest

public class AppIntegrationTest {

    static Controller controller;

    // Before testing connect to the database.
    @BeforeAll
    static void setup() throws InterruptedException {
        // Create controller and connect to Docker DB
        controller = new Controller();

        // Thread is a separate path of execution in java.
        // This allows LocalTestConnection to run without blocking upcoming tests.
        //Thread t = new Thread(() ->  controller.LocalTestConnection());
        //Thread t = new Thread(() ->  controller.dockerTestConnection());
        controller.dockerTestConnection();

        assertNotNull(controller.getConnection(), "Database connection failed");


        // Create new thread and test LocalTestConnection.
       //t.start();

       // If LocalTestConnection finishes, the thread will die 5 seconds after it's done.
        //This allows other tests to begin without needing to wait for connection to try 100 times.
       // If connection fails it will send an error message.
       //t.join(5000);
    }

    /**
     * Test 1. Check that the controller was created succesfully.
     * If null set up failed.
     */
    @Test
    void testDatabaseConnection() {
        assertNotNull(controller, "Controller should not be null");
    }


    /**
     * Test 2. Con
     */

    /**
     * Test 3. Check that the population report file can be generated inside the reports directory.
     */

    @Test
    void testPopulationReportFileCreation() {
        PopulationReports pop = new PopulationReports(controller);

        // Creates a test specific file to avoid overriding non-test file.
        String testFilename = "testWorldPopulation.md";
        String reportContent = pop.getWorldPopulation();

        // Path = reports directory.
        File reportsDir = new File("./reports");
        //reportsDir.mkdir();



        // Generate the report
        controller.printToFile(testFilename, new StringBuilder(reportContent));

        // Check if file was created
        File testReportFile = new File("./reports/" + testFilename);
        assertTrue(testReportFile.exists(), "Integration test report file should exist");

        // Delete the test file after checking it exists.
        // Optional
        //testReportFile.delete();
    }


}
