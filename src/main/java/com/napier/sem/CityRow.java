package com.napier.sem;

/**
 * Represents a single record returned by a city population query.
 * <p>
 * This data transfer object (DTO) is used across the reporting classes
 * to hold formatted city data retrieved from the database.
 * It maintains immutability and contains only public final fields.
 * </p>
 *
 * @author Alan Glowacz
 */
public class CityRow {

    /** Optional group label (e.g., Continent, Region, Country, or "Country — District"). */
    public final String group;

    /** City name. */
    public final String name;

    /** Country name. */
    public final String country;

    /** District name (may be empty or null). */
    public final String district;

    /** City population. */
    public final int population;

    /**
     * Constructs a {@code CityRow} instance representing a single query result.
     *
     * @param group      optional group label (continent, region, country, or district)
     * @param name       city name
     * @param country    country name
     * @param district   district name (nullable)
     * @param population population count
     */
    public CityRow(String group, String name, String country, String district, int population) {
        this.group = group;
        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
    }
}
