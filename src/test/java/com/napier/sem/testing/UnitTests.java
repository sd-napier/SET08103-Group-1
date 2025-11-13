package com.napier.sem.testing;

import com.napier.sem.CityPrinter;
import com.napier.sem.CityRow;
import com.napier.sem.Controller;
import com.napier.sem.PopulationReports;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.testng.AssertJUnit.*;

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

      /////////////////////////////////////////////////////////////////////////////
     //////////////////////CityReports Tests Start Here///////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    /// Test headings constants are correct (ungrouped)
        @Test
        public void testHeadingNoGroup() {
            assertEquals("| City | Country | District | Population |\r\n",
                    CityPrinter.HEADING_NO_GROUP);
        }

        /// Test headings constants are correct (grouped)
        @Test
        public void testHeadingWithGroup() {
            assertEquals("| Group | City | Country | District | Population |\r\n",
                    CityPrinter.HEADING_WITH_GROUP);
        }

        /// Test Markdown row formatting without group column
        @Test
        public void testToMarkdownRows_Ungrouped() {
            List<CityRow> rows = List.of(
                    new CityRow(null, "Paris", "France", "Île-de-France", 2_148_000),
                    new CityRow(null, "Berlin", "Germany", "Berlin", 3_769_000)
            );

            List<String> md = CityPrinter.toMarkdownRows(rows, false);

            assertNotNull(md);
            assertTrue(md.size() >= 3); // heading + 2 rows
            assertEquals("| City | Country | District | Population |\r\n", md.get(0));
            assertTrue(md.get(1).contains("| Paris | France | Île-de-France | 2,148,000 |"));
            assertTrue(md.get(2).contains("| Berlin | Germany | Berlin | 3,769,000 |"));
        }

        /// Test Markdown row formatting with group column
        @Test
        public void testToMarkdownRows_Grouped() {
            List<CityRow> rows = List.of(
                    new CityRow("Europe", "Paris", "France", "Île-de-France", 2_148_000)
            );

            List<String> md = CityPrinter.toMarkdownRows(rows, true);

            assertNotNull(md);
            assertTrue(md.size() >= 2); // heading + 1 row
            assertEquals("| Group | City | Country | District | Population |\r\n", md.get(0));
            assertTrue(md.get(1).startsWith("| Europe | Paris | France | Île-de-France | 2,148,000 |"));
        }

    /// Test CityRow constructor assigns fields correctly (with group)
    @Test
    public void testCityRowWithGroup() {
        CityRow r = new CityRow("Europe", "Paris", "France", "Île-de-France", 2_148_000);

        assertNotNull(r);
        assertEquals("Europe", r.group);
        assertEquals("Paris", r.name);
        assertEquals("France", r.country);
        assertEquals("Île-de-France", r.district);
        assertEquals(2_148_000, r.population);
    }

    /// Test CityRow constructor assigns fields correctly (without group, null district)
    @Test
    public void testCityRowNoGroupNullDistrict() {
        CityRow r = new CityRow(null, "Tokyo", "Japan", null, 13_960_000);

        assertNotNull(r);
        assertNull(r.group);
        assertEquals("Tokyo", r.name);
        assertEquals("Japan", r.country);
        assertNull(r.district);
        assertEquals(13_960_000, r.population);
    }
      ///////////////////////////////////////////////////////////////////////////
     //////////////////////CityReports Tests End Here///////////////////////////
    ///////////////////////////////////////////////////////////////////////////

}