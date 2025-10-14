# Use Case Definitions

## 1. View All Countries by Population
**Actor:** Data Analyst  
**Description:** Retrieves a list of all countries from the SQL database, ordered by descending population.  
**Preconditions:** The SQL database is connected and contains up-to-date country population data.  
**Postconditions:** A report or data table is displayed with all countries listed from largest to smallest population.  
**Main Flow:**
1. Analyst requests the “Countries by Population” report.
2. System sends a query to the SQL database.
3. SQL database returns all country data ordered by population (descending).
4. System displays the report to the analyst.

---

## 2. Retrieve Global Chinese-Speaking Population
**Actor:** Data Analyst  
**Description:** Calculates the total number of people who speak Chinese worldwide and determines the percentage of the global population.  
**Preconditions:** Language and population data exist in the SQL database.  
**Postconditions:** A numerical total and percentage value are displayed.  
**Main Flow:**
1. Analyst requests the “Chinese Speakers” report.
2. System queries SQL database for language and population data.
3. Database returns results.
4. System aggregates totals and calculates percentages.
5. Results displayed in a summary table.

---

## 3. Generate Country Report
**Actor:** Data Analyst / End User  
**Description:** Generates a detailed report containing all relevant information for each country.  
**Preconditions:** SQL database contains up-to-date country data.  
**Postconditions:** A complete report for each country is produced and displayed.  
**Main Flow:**
1. Analyst selects “Country Report”.
2. System queries SQL database for all country information.
3. Database returns detailed results.
4. System displays or exports the report.

---