package com.napier.sem;


/** THIS CLASS IS CURRENTLY UNUSED, BUT CONTAINS A LIST OF ALL THE REPORTS WE NEED TO GENERATE.
 *  WE MAY NOT NET AN EXTERNAL QUERIES CLASS IF WE DESIGN THE REPORTS CLASSES AS POP REPORTS HAS BEEN IMPLEMENTED
 */
public class Queries {

    // Constructor
    public Queries() {

        // These Queries will return the unique name of each Continent, Region, Country, District, Capital and City.
        String Continents = "SELECT DISTINCT Continent FROM Country";
        String Regions = "SELECT DISTINCT Region FROM Country WHERE Continent ="; // will need to append the continent in the method that uses this
        String Countries = "SELECT DISTINCT Name FROM Country";
        String District = "SELECT DISTINCT District FROM City";
        // String Capital =  "SELECT DISTINCT Name FROM City WHERE; <-- this one will need a join on the Country table where : country.capital == city.ID
        String City = "SELECT DISTINCT Name FROM City;";
    }
//          REPORT NEEDED                                                                                                         REPORT GROUP
//          -----------------------------------------------------------------------------------------------------------------------------------
//          All the countries in the world organised by largest population to smallest.                                           COUNTRIES
//          All the countries in a continent organised by largest population to smallest.                                         COUNTRIES
//          All the countries in a region organised by largest population to smallest.                                            COUNTRIES

//          The top N populated countries in the world where N is provided by the user.                                           COUNTRIES
//          The top N populated countries in a continent where N is provided by the user.                                         COUNTRIES
//          The top N populated countries in a region where N is provided by the user.                                            COUNTRIES

//          All the cities in the world organised by largest population to smallest.                                              CITIES
//          All the cities in a continent organised by largest population to smallest.                                            CITIES
//          All the cities in a region organised by largest population to smallest.                                               CITIES
//          All the cities in a country organised by largest population to smallest.                                              CITIES
//          All the cities in a district organised by largest population to smallest.                                             CITIES

//          The top N populated cities in the world where N is provided by the user.                                              CITIES
//          The top N populated cities in a continent where N is provided by the user.                                            CITIES
//          The top N populated cities in a region where N is provided by the user.                                               CITIES
//          The top N populated cities in a country where N is provided by the user.                                              CITIES
//          The top N populated cities in a district where N is provided by the user.                                             CITIES

//          All the capital cities in the world organised by largest population to smallest.                                      CAPITALS
//          All the capital cities in a continent organised by largest population to smallest.                                    CAPITALS
//          All the capital cities in a region organised by largest to smallest.                                                  CAPITALS

//          The top N populated capital cities in the world where N is provided by the user.                                      CAPITALS
//          The top N populated capital cities in a continent where N is provided by the user.                                    CAPITALS
//          The top N populated capital cities in a region where N is provided by the user.                                       CAPITALS

//          The population of people, people living in cities, and people not living in cities in each continent.                 POPULATION
//          The population of people, people living in cities, and people not living in cities in each region.                    POPULATION
//          The population of people, people living in cities, and people not living in cities in each country.                   POPULATION

//          The population of the world.                                                                                          POPULATION
//          The population of a continent.                                                                                        POPULATION
//          The population of a region.                                                                                           POPULATION
//          The population of a country.                                                                                          POPULATION
//          The population of a district.                                                                                         POPULATION
//          The population of a city.                                                                                             POPULATION
    //
//          Population Report
//          For the population reports, the following information is requested:
    //
//          The name of the continent/region/country.
//          The total population of the continent/region/country.                                                                 POPULATION (TIES IN WITH ABOVE STATS)
//          The total population of the continent/region/country living in cities (including a %).
//          The total population of the continent/region/country not living in cities (including a %).
    //
//          Finally, the organisation has asked if it is possible to provide the number of people who speak
//          the following the following languages from greatest number to smallest, including the percentage of the world population: NO, IT IS NOT POSSIBLE - THANKS !!!
    //
//          Chinese.
//          English.
//          Hindi.            // SEPERATE LANGUAGE TAB
//          Spanish.
//          Arabic.

    /** getRegions - a method that returns all the regions in the database
     *
     * @return
     */
    public String getRegions() {
        return "SELECT DISTINCT Region FROM country";
    }

    /** getRegions(Overloaded) - a method that returns all the regions in a user defined continent
     *
     * @param continent
     * @return
     */
    public String getRegions(String continent) {
        return "SELECT DISTINCT Region FROM country WHERE Continent =" + continent;
    }


}