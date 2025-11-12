package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

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
    void testGetEmployee()
    {
        Employee emp = app.getEmployee(255530);
        assertNotNull(emp, "Employee should not be null — check database connection or data");

        if (emp != null) {
            assertEquals(255530, emp.emp_no);
            assertEquals("Ronghao", emp.first_name);
            assertEquals("Garigliano", emp.last_name);
        }
    }
}