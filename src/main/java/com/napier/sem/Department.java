package com.napier.sem;

/**
 * Represents a department in the company
 */

public class Department {
    // Department number (used as primary key in DB)
    private String dept_no;

    // Department name
    private String name;

    // Getter for dept_no
    public String getDept_no() {
        return dept_no;
    }

    // Setter for dept_no
    public void setDept_no(String dept_no) {
        this.dept_no = dept_no;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }
}