package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
    private static Connection con = null;

    /**
     * Connect to MySQL database in Docker
     */
    public void connect()
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
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database... Attempt " + (i+1));
            try
            {
                // Wait 5 seconds before each attempt
                Thread.sleep(5000);

                String host = System.getenv("DB_HOST");
                String port = System.getenv("DB_PORT");
                String user = System.getenv("DB_USER");
                String pass = System.getenv("DB_PASS");

                con = DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":" + port + "/employees?allowPublicKeyRetrieval=true&useSSL=false",
                        user,
                        pass
                );

                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database: " + sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

        if (con == null)
        {
            System.out.println("Could not connect to database after " + retries + " attempts, exiting.");
            System.exit(-1);
        }
    }

    /**
     * Disconnect from database
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
     * Display an employee's info
     */
    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.emp_no + " " + emp.first_name + " " + emp.last_name + "\n" +
                            emp.title + "\n" +
                            "Salary: " + emp.salary + "\n" +
                            emp.dept_name + "\n" +
                            "Manager: " + emp.manager + "\n");
        }
    }

    /**
     * Prints a list of employees and their salaries
     */
    public void printSalaries(ArrayList<Employee> employees)
    {
        // Check employees is not null
        if (employees == null)
        {
            System.out.println("No employees");
            return;
        }

        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s",
                "Emp No", "First Name", "Last Name", "Salary"));

        // Loop over all employees in the list
        for (Employee emp : employees)
        {
            if (emp == null) continue; // skip null employees
            String empString = String.format("%-10s %-15s %-20s %-8s",
                    emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(empString);
        }
    }

    /**
     * Get a single employee by ID
     */
    public static Employee getEmployee(int ID)
    {
        try
        {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT emp_no, first_name, last_name " +
                    "FROM employees WHERE emp_no = " + ID;

            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                return emp;
            }
            else return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    /**
     * Get a Department object from the DB by department name
     */
    public Department getDepartment(String dept_name)
    {
        Department dept = null;

        try
        {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT dept_no, dept_name FROM departments WHERE dept_name = '" + dept_name + "'";
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next())
            {
                dept = new Department();
                dept.setDept_no(rset.getString("dept_no"));
                dept.setName(rset.getString("dept_name"));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get department: " + e.getMessage());
        }

        return dept;
    }

    /**
     * Get all employees in a department with current salaries
     */
    public ArrayList<Employee> getSalariesByDepartment(Department dept)
    {
        ArrayList<Employee> employees = new ArrayList<>();

        if (con == null || dept == null)
        {
            System.out.println("No database connection or department.");
            return employees;
        }

        try
        {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary " +
                    "FROM employees, salaries, dept_emp, departments " +
                    "WHERE employees.emp_no = salaries.emp_no " +
                    "AND employees.emp_no = dept_emp.emp_no " +
                    "AND dept_emp.dept_no = departments.dept_no " +
                    "AND salaries.to_date = '9999-01-01' " +
                    "AND departments.dept_no = '" + dept.getDept_no() + "' " +
                    "ORDER BY employees.emp_no ASC";

            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.salary = rset.getDouble("salary");
                employees.add(emp);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get employees by department: " + e.getMessage());
        }

        return employees;
    }

    /**
     * Main method to test features
     */
    public static void main(String[] args)
    {
        App a = new App();
        a.connect();

        // Test single employee
        Employee emp = getEmployee(255530); // static method, call directly
        a.displayEmployee(emp);

        // Test department salaries
        Department dept = a.getDepartment("Sales");
        if (dept != null)
        {
            ArrayList<Employee> empList = a.getSalariesByDepartment(dept);
            for (Employee e : empList)
            {
                a.displayEmployee(e);
            }
        }
        else
        {
            System.out.println("Department not found.");
        }

        a.disconnect();
    }
}