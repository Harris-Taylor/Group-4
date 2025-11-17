package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
    private static Connection con = null;

    /**
     * Connect to MySQL world database
     */
    public void connect(String location, int delay)
    {
        try
        {
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
                Thread.sleep(delay);

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
     * Disconnect from DB
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
     * Get a country by its code (e.g., 'GBR')
     */
    public Country getCountry(String code)
    {
        try
        {
            Statement stmt = con.createStatement();
            String query =
                    "SELECT Code, Name, Continent, Region, Population, Capital " +
                            "FROM country WHERE Code = '" + code + "'";

            ResultSet rset = stmt.executeQuery(query);

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

        return null;
    }

    /**
     * Get all countries ordered by population descending
     */
    public ArrayList<Country> getCountries()
    {
        ArrayList<Country> countries = new ArrayList<>();

        try
        {
            Statement stmt = con.createStatement();
            String query =
                    "SELECT Code, Name, Continent, Region, Population, Capital " +
                            "FROM country ORDER BY Population DESC";

            ResultSet rset = stmt.executeQuery(query);

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
     * Print country report
     */
    public void printCountries(ArrayList<Country> countries)
    {
        if (countries == null || countries.isEmpty())
        {
            System.out.println("No countries to display.");
            return;
        }

        System.out.printf("%-5s %-40s %-15s %-20s %-12s %-10s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");

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

    public static void main(String[] args)
    {
        App app = new App();

        // Default to local docker
        if (args.length < 1)
            app.connect("localhost:33060", 30000);
        else
            app.connect(args[0], Integer.parseInt(args[1]));

        ArrayList<Country> countries = app.getCountries();
        app.printCountries(countries);

        app.disconnect();
    }
}