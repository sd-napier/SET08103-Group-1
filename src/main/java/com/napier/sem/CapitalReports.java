package com.napier.sem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** This class generates the capital reports
 * @author Stuart C. Alexander
 * @since 19/11/2025
 */
public class CapitalReports {

    ///  Class wide Variables
    private Controller cont;
    private String filename = "capitalReports.md";
    private String headingFormat = " | Name | Country | Population |\r\n";

    /// Value variables for retrieved data
    private String name;
    private String country;
    private String population;

    /// Constructor
    public CapitalReports(Controller cont){
        this.cont = cont;
    }

    /** Returns the column headings to be used for Capital City Reports
     * @author Stuart C. Alexander
     * @since Nov 2025
     * @return
     */
    public String getHeadingFormat() {

        return headingFormat;
    }

    /** Returns the filename to be used for Capital City Reports
     * @author Stuart C. Alexander
     * @since Nov 2025
     * @return
     */
    public String getFilename() {

        return filename;
    }

    public ArrayList<String> getCapitalReportWorld(int limit) {

        ArrayList<String> worldCapitals = new ArrayList<>();

        String query = "SELECT ci.Name as Name, co.Name as Country, ci.Population as Population FROM city ci JOIN country co ON ci.ID = co.Capital ORDER BY ci.Population DESC LIMIT " + limit + ";";

        try {
            ResultSet data = cont.runQuery(query);
            while (data.next()) {
                name = data.getString("Name");
                country = data.getString("Country");
                population = Integer.toString(data.getInt("Population"));
                worldCapitals.add("| " + name + " | " + country + " | " + population + " |\r\n ");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nCAPITAL REPORT(world) QUERY FAILED!");
        }
        return worldCapitals;
    }

    public ArrayList<String> getCapitalReportContinent(String conti,int limit) {

        ArrayList<String> contiCapitals = new ArrayList<>();

        String query = "SELECT ci.Name as Name, co.Name as Country, ci.Population as Population FROM city ci JOIN country co ON ci.ID = co.Capital WHERE co.Continent = '" + conti + "' ORDER BY ci.Population DESC LIMIT " + limit + ";";

        try {
            ResultSet data = cont.runQuery(query);
            while (data.next()) {
                name = data.getString("Name");
                country = data.getString("Country");
                population = Integer.toString(data.getInt("Population"));
                contiCapitals.add("| " + name + " | " + country + " | " + population + " |\r\n ");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nCAPITAL REPORT(continent : " + conti + ") QUERY FAILED!");
        }
        return contiCapitals;
    }

    public ArrayList<String> getCapitalReportRegion(String regi,int limit) {

        ArrayList<String> regiCapitals = new ArrayList<>();

        String query = "SELECT ci.Name as Name, co.Name as Country, ci.Population as Population FROM city ci JOIN country co ON ci.ID = co.Capital WHERE co.Region = '" + regi + "' ORDER BY ci.Population DESC LIMIT " + limit + ";";

        try {
            ResultSet data = cont.runQuery(query);
            while (data.next()) {
                name = data.getString("Name");
                country = data.getString("Country");
                population = Integer.toString(data.getInt("Population"));
                regiCapitals.add("| " + name + " | " + country + " | " + population + " |\r\n ");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nCAPITAL REPORT(region : " + regi + ") QUERY FAILED!");
        }
        return regiCapitals;
    }
}
