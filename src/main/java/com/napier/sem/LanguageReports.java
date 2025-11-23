package com.napier.sem;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** LanguqageReports - A java class contatining the code and queries to retrieve the data needed to print the language reports
 * @author Stuart C. Alexander
 * @since Nov 2025
 */
public class LanguageReports {

    /// Class wide Variables
    private Controller cont;
    private String filename = "languageReports.md";
    private String headingFormat = "| Language | Number of Speakers | % of World Population |\r\n";
    private String language;
    private int totalSpeakers = 0;
    private float totalPercentage = 0.00f;

    /// Constructor
    public LanguageReports(Controller cont) {
        this.cont = cont;
    }

    /** getHeadingFormat - Returns the heading format for the language reports
     * @author Stuart C. Alexander
     * @since Nov 2025
     * @return headingFormat
     */
    public String getHeadingFormat() {
        return headingFormat;
    }

    /** getFilename - Returns the filename for language reports .md file
     * @author Stuart C. Alexander
     * @since Nov 2025
     */
    public String getFilename() {
        return filename;
    }

    public ArrayList<String> getLanguageReport() {

        ArrayList<String> results = new ArrayList<>();

        ///  Query to select language, total speakers and language percentage for each language
        String query = "SELECT l.Language, ROUND(SUM(c.Population * l.Percentage / 100)) AS TotalSpeakers, ROUND(SUM(c.Population * l.Percentage / 100) / (SELECT SUM(Population) FROM country) * 100, 2) AS Percent FROM countrylanguage l JOIN country c ON l.CountryCode = c.Code WHERE l.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') GROUP BY l.Language ORDER BY TotalSpeakers DESC;";

        ///  Send query to the controller method to run it on database
        try  {
            /// Result set to store query results (CHANGE FROM .runQueryLocal TO .runQuery AFTER TESTING)
            ResultSet data = cont.runQuery(query);
            while (data.next()) {
                language = data.getString("Language");
                totalSpeakers = data.getInt("TotalSpeakers");
                totalPercentage = data.getFloat("Percent");
                results.add("| " + language + " | " + totalSpeakers + " | " + totalPercentage + "% |\r\n");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage() + "\nLANGUAGE REPORT QUERY FAILED!");
        }
        return (results);
    }
}
