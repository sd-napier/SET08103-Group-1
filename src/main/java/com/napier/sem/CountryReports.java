package com.napier.sem;

import javax.naming.Name;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author stual
 * @date 18/11/2025
 */
public class CountryReports {

    ///  Class Wide Variables
    private Controller cont;
    private String filename = "countryReports.md";
    private String headingFormat = "| Code | Name | Continent | Region | Population | Capital |\r\n";
    /// Value variables
    private String code;
    private String name;
    private String continent;
    private String region;
    private String population;
    private String capital;

    /// Constructor
    public CountryReports(Controller cont) {
        this.cont = cont;
    }

    /** getHeadingFormat - Returns the heading format for the Country reports
     * @author Stuart C. Alexander
     * @since Nov 2025
     * @return headingFormat
     */
    public String getHeadingFormat() {
        return headingFormat;
    }

    /** getFilename - Returns the filename for Country reports .md file
     * @author Stuart C. Alexander
     * @since Nov 2025
     */
    public String getFilename() {
        return filename;
    }

    /**
     *
     * @param limit
     * @return
     */
    public ArrayList<String> getCountryReportWorld(int limit) {

        ArrayList<String> worldCountries = new ArrayList<>();

        String query = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, c2.Name AS Capital FROM country c JOIN city c2 ON c.Capital = c2.ID ORDER BY c.Population DESC LIMIT " +  limit + ";";

        try {
            /// Result set to store query results (CHANGE FROM .runQueryLocal TO .runQuery AFTER TESTING)
            ResultSet data = cont.runQueryLocal(query);
            while (data.next()) {
                code = data.getString("Code");
                name = data.getString("Name");
                continent = data.getString("Continent");
                region = data.getString("Region");
                population = data.getString("Population");
                capital = data.getString("Capital");
                worldCountries.add("| " + code + " | " + name + " | " + continent + " | " + region + " | " + population  + " | " + capital + "|\r\n");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage() + "\nCOUNTRY REPORT(WORLD) QUERY FAILED!");
        }
        return worldCountries;
    }

    /**
     *
     * @param conti
     * @param limit
     * @return
     */
    public ArrayList<String> getCountryReportContinent(String conti, int limit) {

        ArrayList<String> continentCountries = new ArrayList<>();

        String query = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, c2.Name AS Capital FROM country c JOIN city c2 ON c.Capital = c2.ID WHERE c.Continent = '" + conti + "' ORDER BY c.Population DESC LIMIT " +  limit + ";";

        try {
            /// Result set to store query results (CHANGE FROM .runQueryLocal TO .runQuery AFTER TESTING)
            ResultSet data = cont.runQueryLocal(query);
            while (data.next()) {
                code = data.getString("Code");
                name = data.getString("Name");
                continent = data.getString("Continent");
                region = data.getString("Region");
                population = data.getString("Population");
                capital = data.getString("Capital");
                continentCountries.add("| " + code + " | " + name + " | " + continent + " | " + region + " | " + population  + " | " + capital + "|\r\n");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage() + "\nCOUNTRY REPORT(Continent= " + conti + ") QUERY FAILED!");
        }
        return continentCountries;
    }

    /**
     *
     * @param regi
     * @param limit
     * @return
     */
    public ArrayList<String> getCountryReportRegion(String regi, int limit) {

        ArrayList<String> regionCountries = new ArrayList<>();

        String query = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, c2.Name AS Capital FROM country c JOIN city c2 ON c.Capital = c2.ID WHERE c.Region = '" + regi + "' ORDER BY c.Population DESC LIMIT " +  limit + ";";

        try {
            /// Result set to store query results (CHANGE FROM .runQueryLocal TO .runQuery AFTER TESTING)
            ResultSet data = cont.runQueryLocal(query);
            while (data.next()) {
                code = data.getString("Code");
                name = data.getString("Name");
                continent = data.getString("Continent");
                region = data.getString("Region");
                population = data.getString("Population");
                capital = data.getString("Capital");
                regionCountries.add("| " + code + " | " + name + " | " + continent + " | " + region + " | " + population  + " | " + capital + "|\r\n");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage() + "\nCOUNTRY REPORT(Region= " + regi + ") QUERY FAILED!");
        }
        return regionCountries;
    }
}
