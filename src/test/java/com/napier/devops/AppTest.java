package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Unit tests for the printCountries method in the App class.
 */
public class AppTest
{
    /**
     * The App instance used for running output tests
     */
    static App app;

    /**
     * Initialises the App object before running tests.
     */
    @BeforeAll
    static void init()
    {
        app = new App();
    }

    /**
     * Tests printCountries with a null input lists.
     */
    @Test
    void printCountriesTestNull()
    {
        app.printCountries(null);
    }

    /**
     * Tests printCountries with an empty list.
     */
    @Test
    void printCountriesTestEmpty()
    {
        ArrayList<Country> countries = new ArrayList<>();
        app.printCountries(countries);
    }

    /**
     * Tests printCountries with a list containing a null element.
     */
    @Test
    void printCountriesTestContainsNull()
    {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.printCountries(countries);
    }

    /**
     * Tests printCountries with a valid Country object.
     */
    @Test
    void printCountries()
    {
        ArrayList<Country> countries = new ArrayList<>();
        Country c = new Country();
        c.code = "GBR";
        c.name = "United Kingdom";
        c.region = "Northern Europe";
        c.continent = "Europe";
        c.population = 67886011;
        countries.add(c);

        app.printCountries(countries);
    }
}