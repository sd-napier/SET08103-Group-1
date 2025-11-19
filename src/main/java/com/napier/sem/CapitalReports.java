package com.napier.sem;

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

}
