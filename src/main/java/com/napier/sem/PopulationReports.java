package com.napier.sem;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/** PopulationReports Class - generates all population reports
 * @author Stuart C. Alexander ESQ.
 * @since Nov 2025
 */
public class PopulationReports {

    /// Class Variables
    Controller cont;
    private String filename = "populationReports.md";
    String headingFormat = "| Name | Population | Total in Cities | % in Cities | Not in cities | % not in cities |\r\n";

    ///  Variables for holding column info
    String name;
    String population;
    String totalCity;
    String percentCity;
    String notCity;
    String percentNot;
    DecimalFormat bigNumFormat = new DecimalFormat("#,###");

    /** Constructor
     *
     */
    public PopulationReports(Controller cont) {
        this.cont = cont;
    }

    /** Returns heading format forPop Reports Output
     * @return headingFormat
     */
    public String getHeadings() {

        return headingFormat;
    }

    /** This method returns the filename to be written to for Population Reports
     * @return filename
     */
    public String getFilename() {

        return filename;
    }

    /** getWorldPopulation - A Method for querying the database and returning the table row for 'World' in the Population Reports.md
     * @author Stuart C. Alexander
     * @since Nov 2025
     * @return String value containing data for world population
     */
    public String getWorldPopulation() {

        name = "World";
        String query = "SELECT SUM(Population) as world_pop FROM ";

        try {
            /// Result sets to store query results (CHANGE FROM .runQueryLocal TO .runQuery AFTER TESTING)
            ResultSet worldData = cont.runQueryLocal(query + "country;");
            ResultSet cityData = cont.runQueryLocal(query + "city;");

            /// BigIntegers required to deal with billions
            BigInteger worldPop = null;
            BigInteger cityPop = null;

            /// floats for working out percentages
            float smallerPop = 0.00f;
            float smallerCity = 0.00f;

            /// get the results of the query from the country table, set population string and make number smaller to work out percentages
            while (worldData.next()) {
                worldPop = BigInteger.valueOf(worldData.getLong("world_pop"));
                population = bigNumFormat.format(worldPop);
                smallerPop = Math.round(worldPop.divide(BigInteger.valueOf(100)).floatValue());
            }
            /// get the results from city table, set totalCity string and divide BigInteger into smaller number to work on percentages
            while (cityData.next()) {
                cityPop = BigInteger.valueOf(cityData.getLong("world_pop"));
                totalCity = bigNumFormat.format(cityPop);
                smallerCity = Math.round(cityPop.divide(BigInteger.valueOf(100)).floatValue());
            }
            /// Work out how many are not in cities, and percentages for city dwellers/ non-city dwellers
            notCity = bigNumFormat.format(worldPop.subtract(cityPop));
            float perCity = ((smallerCity / smallerPop) * 100);
            float perNotCity = 100 - perCity;
            percentCity = String.format("%.2f", perCity);
            percentNot = String.format("%.2f", perNotCity);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nQuery Failed!");
        }
        /// Return string containing all values
        return ("|" + name + " | " + population + " | " +  totalCity + " | " + percentCity + "% | " + notCity + " | " + percentNot + "% |\r\n");
    }
    /** getWorldPopulation - A Method for querying the database and returning the table rows for 'Continents', 'Regions' and 'countries' in the Population Reports.md
     * @author Stuart C. Alexander
     * @since Nov 2025
     * @return String value containing data for world population
     */
    public ArrayList<String> getPopulation(String type) {

        ArrayList<String> results = new ArrayList<>();

        /// BigIntegers required to deal with billions
        BigInteger worldPop = null;
        BigInteger cityPop = null;

        /// floats for working out percentages
        float smallerPop = 0.00f;
        float smallerCity = 0.00f;

        /// Tries to process the data from the two queries and works out city dwellers/ non-city dwellers and percentages.
        try {
            /// Result set to store query results (CHANGE FROM .runQueryLocal TO .runQuery AFTER TESTING)
            ResultSet worldData = cont.runQueryLocal("SELECT " + type + ", SUM(Population) as pop FROM country GROUP BY " + type + ";");
            while (worldData.next()) {
                name = worldData.getString(type);
                worldPop = BigInteger.valueOf(worldData.getLong("pop"));
                population = bigNumFormat.format(worldPop);
                smallerPop = Math.round(worldPop.divide(BigInteger.valueOf(100)).floatValue());

                /// Result set to store query results (CHANGE FROM .runQueryLocal TO .runQuery AFTER TESTING)
                ResultSet cityData = cont.runQueryLocal("SELECT SUM(c.Population) AS pop FROM city c JOIN country co ON c.CountryCode = co.Code WHERE co." + type + " = '" + name + "';");
                while (cityData.next()) {
                    cityPop = BigInteger.valueOf(cityData.getLong("pop"));
                    totalCity = bigNumFormat.format(cityPop);
                    smallerCity = Math.round(cityPop.divide(BigInteger.valueOf(100)).floatValue());
                    notCity = bigNumFormat.format(worldPop.subtract(cityPop));
                    float perCity = ((smallerCity / smallerPop) * 100);
                    float perNotCity = 100 - perCity;
                    percentCity = String.format("%.2f", perCity);
                    percentNot = String.format("%.2f", perNotCity);
                }
                results.add("|" + name + " | " + population + " | " +  totalCity + " | " + percentCity + "% | " + notCity + " | " + percentNot + "% |\r\n");
            }
            ///  Catch SQL exception and print sysout message
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nQuery Failed!");
        }

        return(results);
    }
}