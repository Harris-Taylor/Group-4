package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * Main application class for connecting to the MySQL 'world' database,
 * retrieving country data, and printing reports.
 */
public class App
{
    // Holds the connection to the MySQL database
    private static Connection con = null;

    /**
     * Connects to the MySQL world database using the provided server location.
     * Retries up to 10 times with a delay between attempts.
     *
     * @param location the host and port of the database (e.g., "localhost:33060")
     * @param delay milliseconds to wait between retry attempts
     */
    public void connect(String location, int delay)
    {
        try
        {
            // Loads the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; i++)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait before retrying
                Thread.sleep(delay);

                // Attempt to connect to the database
                con = DriverManager.getConnection(
                        "jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example"
                );

                System.out.println("Successfully connected");
                break;
            }
            catch (Exception e)
            {
                System.out.println("Connection attempt failed: " + e.getMessage());
            }
        }
    }

    /**
     * Closes the connection to the database if it is open.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try { con.close(); }
            catch (Exception e) { System.out.println("Error closing connection"); }
        }
    }

    /**
     * Retrieves a single country from the database using its 3-letter country code.
     *
     * @param code the country's code (e.g., "GBR", "USA")
     * @return a Country object filled with database values, or null if not found
     */
    public Country getCountry(String code)
    {
        try
        {
            Statement stmt = con.createStatement();

            // SQL query to fetch a country by code
            String query =
                    "SELECT Code, Name, Continent, Region, Population, Capital " +
                            "FROM country WHERE Code = '" + code + "'";

            ResultSet rset = stmt.executeQuery(query);

            // If data exists, create and return a Country object
            if (rset.next())
            {
                Country c = new Country();
                c.code = rset.getString("Code");
                c.name = rset.getString("Name");
                c.continent = rset.getString("Continent");
                c.region = rset.getString("Region");
                c.population = rset.getInt("Population");
                c.capital = rset.getInt("Capital");
                return c;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error fetching country: " + e.getMessage());
        }

        return null; // No result found
    }

    /**
     * Retrieves all countries from the database ordered by population descending.
     *
     * @return an ArrayList of Country objects
     */
    public ArrayList<Country> getCountries()
    {
        ArrayList<Country> countries = new ArrayList<>();

        try
        {
            Statement stmt = con.createStatement();

            // SQL query to get all countries ordered by population
            String query =
                    "SELECT Code, Name, Continent, Region, Population, Capital " +
                            "FROM country ORDER BY Population DESC";

            ResultSet rset = stmt.executeQuery(query);

            // Loop through results and create Country objects
            while (rset.next())
            {
                Country c = new Country();
                c.code = rset.getString("Code");
                c.name = rset.getString("Name");
                c.continent = rset.getString("Continent");
                c.region = rset.getString("Region");
                c.population = rset.getInt("Population");
                c.capital = rset.getInt("Capital");
                countries.add(c);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to fetch countries: " + e.getMessage());
        }

        return countries;
    }

    /**
     * Prints a formatted table of country information to the console.
     *
     * @param countries list of Country objects to print
     */
    public void printCountries(ArrayList<Country> countries)
    {
        if (countries == null || countries.isEmpty())
        {
            System.out.println("No countries to display.");
            return;
        }

        // Print table header
        System.out.printf("%-5s %-40s %-15s %-20s %-12s %-10s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");

        // Print each country row
        for (Country c : countries)
        {
            if (c == null)
            {
                System.out.println("Null country record.");
                continue;
            }

            System.out.printf("%-5s %-40s %-15s %-20s %-12d %-10s%n",
                    c.code, c.name, c.continent, c.region, c.population, c.capital);
        }
    }

    /**
     * The main entry point of the application.
     * Connects to the database, retrieves all countries, prints them, and disconnects.
     */
    public static void main(String[] args)
    {
        App app = new App();

        // If no arguments are provided, use default local Docker database settings
        if (args.length < 1)
            app.connect("localhost:33060", 30000);
        else
            app.connect(args[0], Integer.parseInt(args[1]));

        // Fetch and print all countries
        ArrayList<Country> countries = app.getCountries();
        app.printCountries(countries);

        // Close the database connection
        app.disconnect();
    }
}