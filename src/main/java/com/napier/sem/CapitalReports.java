package com.napier.sem;

import java.util.ArrayList;

/** This class generates the capital reports
 * @author Stuart C. Alexander
 * @since 19/11/2025
 */
public class CapitalReports {

    private Controller cont;
    private String filename = "capitalReports.md";
    private String headingFormat = " | Name | Country | Population |\r\n";



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



        return  worldCapitals;
    }
}
