# Bajaj Finserv Health App (Qualifier 1)

This Spring Boot application:
- Generates a webhook on startup
- Solves SQL Question 2 (younger employee count)
- Posts the solution (final SQL query) to the webhook using JWT Authorization.

## SQL Solution
```sql
SELECT e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME, 
       COUNT(CASE WHEN e2.DOB > e.DOB THEN 1 END) AS YOUNGER_EMPLOYEES_COUNT
FROM EMPLOYEE e
JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
LEFT JOIN EMPLOYEE e2 ON e.DEPARTMENT = e2.DEPARTMENT
GROUP BY e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME, e.DOB
ORDER BY e.EMP_ID DESC;
