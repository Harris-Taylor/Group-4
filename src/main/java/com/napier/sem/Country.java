package com.napier.sem;

/**
 * Represents a country stored in the MySQL world database.
 */
public class Country {

    /**
     * The 3-letter country code (primary key in the database)
     */
    public String code;

    /**
     * The name of the country (e.g., "United Kingdom", "Japan")
     */
    public String name;

    /**
     * The continent that the country is in (e.g., "Europe", "South America")
     */
    public String continent;

    /**
     * The region that the continent is in (e.g., "Western Europe", "Eastern Asia")
     */
    public String region;

    /**
     * The population of the country
     */
    public int population;

    /**
     * The ID of the capital city (foreign key referencing the city table)
     */
    public int capital;
}