package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);
    }

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