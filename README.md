# Bajaj Finserv Health App (Qualifier 1)

This Spring Boot application:
- Generates a webhook on startup
- Solves SQL Question 2 (younger employee count)
- Posts the solution (final SQL query) to the webhook using JWT Authorization.
## SQL Question
There are three tables: -
1. DEPARTMENT: Contains details about the department.
 DEPARTMENT_ID (Primary Key)
 DEPARTMENT_NAME
2. EMPLOYEE: Contains employee details.
 EMP_ID (Primary Key)
 FIRST_NAME
 LAST_NAME
 DOB (Date of Birth)
 GENDER
 DEPARTMENT (Foreign Key referencing DEPARTMENT_ID in DEPARTMENT)
3. PAYMENTS: Contains salary payment records.
 PAYMENT_ID (Primary Key)
 EMP_ID (Foreign Key referencing EMP_ID in EMPLOYEE)
 AMOUNT (Salary credited)
 PAYMENT_TIME (Date and time of the transaction)

Problem Statement:
You are required to calculate the number of employees who are younger than each
employee, grouped by their respective departments. For each employee, return the
count of employees in the same department whose age is less than theirs.
Output Format:
• The output should contain the following columns:
1. EMP_ID: The ID of the employee.
2. FIRST_NAME: The first name of the employee.
3. LAST_NAME: The last name of the employee.
4. DEPARTMENT_NAME: The name of the department the employee
belongs to.
5. YOUNGER_EMPLOYEES_COUNT: The number of employees who are
younger than the respective employee in their department.
The output should be ordered by employee ID in descending order.
## SQL Solution
```sql
SELECT e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME, 
       COUNT(CASE WHEN e2.DOB > e.DOB THEN 1 END) AS YOUNGER_EMPLOYEES_COUNT
FROM EMPLOYEE e
JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
LEFT JOIN EMPLOYEE e2 ON e.DEPARTMENT = e2.DEPARTMENT
GROUP BY e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME, e.DOB
ORDER BY e.EMP_ID DESC;
--------------------OR----------------------------------------
SELECT 
    e.EMP_ID,
    e.FIRST_NAME,
    e.LAST_NAME,
    d.DEPARTMENT_NAME,
    COUNT(CASE WHEN e2.DOB > e.DOB THEN 1 END) AS YOUNGER_EMPLOYEES_COUNT
FROM EMPLOYEE e
JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
LEFT JOIN EMPLOYEE e2 ON e.DEPARTMENT = e2.DEPARTMENT
GROUP BY e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME, e.DOB
ORDER BY e.EMP_ID DESC;
