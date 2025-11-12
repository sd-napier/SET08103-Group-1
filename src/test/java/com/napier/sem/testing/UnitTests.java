package com.napier.sem.testing;

import com.napier.sem.Controller;
import com.napier.sem.PopulationReports;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/** JUnit Test Class for testing application methods
 * @author Stuart C. Alexander
 * @since Nov 2025
 */
public class UnitTests {

    /// Test Controller instantiation
    @Test
    public void testControllerCreation()  {
        Controller cont = new Controller();

        assertNotNull(cont);
    }

    /// Test PopulationReports instantiation
    @Test
    public void testPopReportCreation() {
        Controller cont = new Controller();
        PopulationReports pop = new PopulationReports(cont);

        assertNotNull(pop);
    }

    /// Test PopulationReports Filename is correct
    @Test
    public void testPopReportsFilename() {
        Controller cont = new Controller();
        PopulationReports pop = new PopulationReports(cont);

        assertEquals("populationReports.md", pop.getFilename());
    }

    /// Test PopulationReports Headings are correct
    @Test
    public void testPopReportsHeadings() {
        Controller cont = new Controller();
        PopulationReports pop = new PopulationReports(cont);

        assertEquals("| Name | Population | Total in Cities | % in Cities | Not in cities | % not in cities |\r\n", pop.getHeadings());
    }


}