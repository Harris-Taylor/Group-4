package com.napier.sem;

/**
 * Represents a city stored in the MySQL world database.
 */
public class City {

    /**
     * The id of the city (primary key in the database)
     */
    public int id;

    /**
     * The name of the city (e.g., "London", "Tokyo")
     */
    public String name;

    /**
     * The 3-letter country code indicating which country the city belongs to (e.g., "GBR", "JPN")
     */
    public String countryCode;

    /**
     * The district or state the city is located in (e.g., "England", "California")
     */
    public String district;

    /**
     * population of the city
     */
    public int population;
}