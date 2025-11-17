package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the App class.
 * These tests connect to the real MySQL world database and verify retrieving the data.
 */
public class AppIntegrationTest
{
    /**
     * The App instance used to run database queries during the tests
     */
    static App app;

    /**
     * Sets up the database connection before any tests are run.
     * Connects to the local MySQL instance running in Docker.
     */
    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);
    }

    /**
     * Tests the getCountry method by retrieving a known country ("GBR")
     * and verifying that the data returned matches expected values.
     */
    @Test
    void testGetCountry()
    {
        // "GBR" (United Kingdom) always exists in world.sql
        Country country = app.getCountry("GBR");

        assertNotNull(country, "Country should not be null — check DB connection or data");

        if (country != null) {
            assertEquals("GBR", country.code);
            assertEquals("United Kingdom", country.name);
            assertEquals("Europe", country.continent);
        }
    }
}