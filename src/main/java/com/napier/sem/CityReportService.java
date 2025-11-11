package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides data retrieval operations for city-based reports.
 * <p>
 * This service depends on a valid JDBC {@link Connection} to the world database and
 * supplies methods for retrieving cities by population across multiple scopes:
 * world, continent, region, country, and district.
 * Each method accepts an optional integer limit parameter; when {@code null} or ≤ 0,
 * the default limit of 32 is applied.
 * </p>
 *
 * @author Alan Glowacz
 */
public class CityReportService {

    /** Active JDBC connection used for all queries. */
    private final Connection con;

    /**
     * Constructs a {@code CityReportService} with a given database connection.
     *
     * @param con active JDBC connection to the world database
     */
    public CityReportService(Connection con) {
        this.con = con;
    }

    /**
     * Returns the specified limit or a default value of 32 if the input is null or non-positive.
     *
     * @param n optional limit value
     * @return {@code n} if valid, otherwise 32
     */
    private static int limitOrDefault(Integer n) {
        return (n == null || n <= 0) ? 32 : n;
    }

    /**
     * Maps a {@link ResultSet} to a list of {@link CityRow} objects.
     *
     * @param rs result set containing city data
     * @return list of mapped {@code CityRow} records
     * @throws SQLException if a database access error occurs
     */
    private static List<CityRow> map(ResultSet rs) throws SQLException {
        List<CityRow> out = new ArrayList<>();
        while (rs.next()) {
            out.add(new CityRow(
                    rs.getString("grp"),
                    rs.getString("Name"),
                    rs.getString("Country"),
                    rs.getString("District"),
                    rs.getInt("Population")
            ));
        }
        return out;
    }

    /**
     * Retrieves the top N cities in the world by population.
     *
     * @param n number of cities to return
     * @return list of top N cities globally
     * @throws SQLException if a database access error occurs
     */
    public List<CityRow> worldCities(Integer n) throws SQLException {
        int lim = limitOrDefault(n);
        String sql = """
            SELECT NULL AS grp, c.Name, co.Name AS Country, c.District, c.Population
            FROM city c
            JOIN country co ON co.Code = c.CountryCode
            ORDER BY c.Population DESC, c.ID ASC
            LIMIT ?;
        """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, lim);
            try (ResultSet rs = ps.executeQuery()) {
                return map(rs);
            }
        }
    }

    /**
     * Retrieves the top N cities by population for each continent.
     *
     * @param nPerContinent number of cities to return per continent
     * @return list of top cities grouped by continent
     * @throws SQLException if a database access error occurs
     */
    public List<CityRow> continentCities(Integer nPerContinent) throws SQLException {
        int lim = limitOrDefault(nPerContinent);
        String sql = """
            WITH ranked AS (
              SELECT
                co.Continent AS grp,
                c.Name,
                co.Name AS Country,
                c.District,
                c.Population,
                ROW_NUMBER() OVER (PARTITION BY co.Continent ORDER BY c.Population DESC, c.ID ASC) AS rn
              FROM city c
              JOIN country co ON co.Code = c.CountryCode
            )
            SELECT grp, Name, Country, District, Population
            FROM ranked
            WHERE rn <= ?
            ORDER BY grp, rn;
        """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, lim);
            try (ResultSet rs = ps.executeQuery()) {
                return map(rs);
            }
        }
    }

    /**
     * Retrieves the top N cities by population for each region.
     *
     * @param nPerRegion number of cities to return per region
     * @return list of top cities grouped by region
     * @throws SQLException if a database access error occurs
     */
    public List<CityRow> regionCities(Integer nPerRegion) throws SQLException {
        int lim = limitOrDefault(nPerRegion);
        String sql = """
            WITH ranked AS (
              SELECT
                co.Region AS grp,
                c.Name,
                co.Name AS Country,
                c.District,
                c.Population,
                ROW_NUMBER() OVER (PARTITION BY co.Region ORDER BY c.Population DESC, c.ID ASC) AS rn
              FROM city c
              JOIN country co ON co.Code = c.CountryCode
            )
            SELECT grp, Name, Country, District, Population
            FROM ranked
            WHERE rn <= ?
            ORDER BY grp, rn;
        """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, lim);
            try (ResultSet rs = ps.executeQuery()) {
                return map(rs);
            }
        }
    }

    /**
     * Retrieves the top N cities for the top M countries ranked by population.
     *
     * @param maxCountries maximum number of countries to include
     * @param nPerCountry  number of cities to return per country
     * @return list of top cities grouped by country
     * @throws SQLException if a database access error occurs
     */
    public List<CityRow> countryCities(Integer maxCountries, Integer nPerCountry) throws SQLException {
        int countryLim = limitOrDefault(maxCountries);
        int cityLim = limitOrDefault(nPerCountry);
        String sql = """
            WITH top_countries AS (
              SELECT Code, Name, ROW_NUMBER() OVER (ORDER BY Population DESC, Code ASC) AS rnk
              FROM country
            ),
            ranked AS (
              SELECT
                tc.Name AS grp,
                c.Name,
                co.Name AS Country,
                c.District,
                c.Population,
                ROW_NUMBER() OVER (PARTITION BY tc.Code ORDER BY c.Population DESC, c.ID ASC) AS rn
              FROM top_countries tc
              JOIN country co ON co.Code = tc.Code
              JOIN city c ON c.CountryCode = tc.Code
              WHERE tc.rnk <= ?
            )
            SELECT grp, Name, Country, District, Population
            FROM ranked
            WHERE rn <= ?
            ORDER BY grp, rn;
        """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, countryLim);
            ps.setInt(2, cityLim);
            try (ResultSet rs = ps.executeQuery()) {
                return map(rs);
            }
        }
    }

    /**
     * Retrieves the top N cities for the top M districts ranked by total population.
     *
     * @param maxDistricts maximum number of districts to include
     * @param nPerDistrict number of cities to return per district
     * @return list of top cities grouped by district
     * @throws SQLException if a database access error occurs
     */
    public List<CityRow> districtCities(Integer maxDistricts, Integer nPerDistrict) throws SQLException {
        int districtLim = limitOrDefault(maxDistricts);
        int cityLim = limitOrDefault(nPerDistrict);
        String sql = """
            WITH district_pop AS (
              SELECT CountryCode, District, SUM(Population) AS total_pop
              FROM city
              WHERE District IS NOT NULL AND District <> ''
              GROUP BY CountryCode, District
            ),
            top_districts AS (
              SELECT CountryCode, District,
                     ROW_NUMBER() OVER (ORDER BY total_pop DESC, CountryCode ASC, District ASC) AS rnk
              FROM district_pop
            ),
            ranked AS (
              SELECT
                CONCAT(co.Name, ' — ', td.District) AS grp,
                c.Name,
                co.Name AS Country,
                c.District,
                c.Population,
                ROW_NUMBER() OVER (
                  PARTITION BY td.CountryCode, td.District
                  ORDER BY c.Population DESC, c.ID ASC
                ) AS rn
              FROM top_districts td
              JOIN city c ON c.CountryCode = td.CountryCode AND c.District = td.District
              JOIN country co ON co.Code = td.CountryCode
              WHERE td.rnk <= ?
            )
            SELECT grp, Name, Country, District, Population
            FROM ranked
            WHERE rn <= ?
            ORDER BY grp, rn;
        """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, districtLim);
            ps.setInt(2, cityLim);
            try (ResultSet rs = ps.executeQuery()) {
                return map(rs);
            }
        }
    }

    /**
     * Retrieves the top N cities in a specific continent by population.
     *
     * @param continent name of the continent
     * @param n         number of cities to return
     * @return list of cities within the specified continent
     * @throws SQLException if a database access error occurs
     */
    @SuppressWarnings("unused") // exposed for tests and future menu routes
    public List<CityRow> citiesInContinent(String continent, Integer n) throws SQLException {
        int lim = limitOrDefault(n);
        String sql = """
            SELECT ? AS grp, c.Name, co.Name AS Country, c.District, c.Population
            FROM city c
            JOIN country co ON co.Code = c.CountryCode
            WHERE co.Continent = ?
            ORDER BY c.Population DESC, c.ID ASC
            LIMIT ?;
        """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, continent);
            ps.setString(2, continent);
            ps.setInt(3, lim);
            try (ResultSet rs = ps.executeQuery()) {
                return map(rs);
            }
        }
    }

    /**
     * Retrieves the top N cities in a specific region by population.
     *
     * @param region name of the region
     * @param n      number of cities to return
     * @return list of cities within the specified region
     * @throws SQLException if a database access error occurs
     */
    @SuppressWarnings("unused") // exposed for tests and future menu routes
    public List<CityRow> citiesInRegion(String region, Integer n) throws SQLException {
        int lim = limitOrDefault(n);
        String sql = """
            SELECT ? AS grp, c.Name, co.Name AS Country, c.District, c.Population
            FROM city c
            JOIN country co ON co.Code = c.CountryCode
            WHERE co.Region = ?
            ORDER BY c.Population DESC, c.ID ASC
            LIMIT ?;
        """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, region);
            ps.setString(2, region);
            ps.setInt(3, lim);
            try (ResultSet rs = ps.executeQuery()) {
                return map(rs);
            }
        }
    }

    /**
     * Retrieves the top N cities within a specific country, identified by country code.
     *
     * @param countryCode ISO country code
     * @param n           number of cities to return
     * @return list of cities within the specified country
     * @throws SQLException if a database access error occurs
     */
    @SuppressWarnings("unused") // exposed for tests and future menu routes
    public List<CityRow> citiesInCountryCode(String countryCode, Integer n) throws SQLException {
        int lim = limitOrDefault(n);
        String sql = """
            SELECT co.Name AS grp, c.Name, co.Name AS Country, c.District, c.Population
            FROM city c
            JOIN country co ON co.Code = c.CountryCode
            WHERE co.Code = ?
            ORDER BY c.Population DESC, c.ID ASC
            LIMIT ?;
        """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, countryCode);
            ps.setInt(2, lim);
            try (ResultSet rs = ps.executeQuery()) {
                return map(rs);
            }
        }
    }

    /**
     * Retrieves the top N cities within a specific district of a given country.
     *
     * @param countryCode ISO country code
     * @param district    name of the district
     * @param n           number of cities to return
     * @return list of cities within the specified district
     * @throws SQLException if a database access error occurs
     */
    @SuppressWarnings("unused") // exposed for tests and future menu routes
    public List<CityRow> citiesInDistrict(String countryCode, String district, Integer n) throws SQLException {
        int lim = limitOrDefault(n);
        String sql = """
            SELECT CONCAT(co.Name, ' — ', c.District) AS grp,
                   c.Name, co.Name AS Country, c.District, c.Population
            FROM city c
            JOIN country co ON co.Code = c.CountryCode
            WHERE c.CountryCode = ? AND c.District = ?
            ORDER BY c.Population DESC, c.ID ASC
            LIMIT ?;
        """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, countryCode);
            ps.setString(2, district);
            ps.setInt(3, lim);
            try (ResultSet rs = ps.executeQuery()) {
                return map(rs);
            }
        }
    }
}
