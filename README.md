# Bajaj Finserv Health App (Qualifier 1)

This Spring Boot application:
- Generates a webhook on startup
- Solves SQL Question 2 (younger employee count)
- Posts the solution (final SQL query) to the webhook using JWT Authorization.

## BajajFinservHealthApp
BajajFinservHealthApp/
├── src/main/java/
│ └── com/bajajfinserv/health/
│ ├── BajajFinservHealthAppApplication.java # Main Spring Boot application class
│ ├── config/
│ │ └── WebClientConfig.java # WebClient/RestTemplate configuration
│ ├── dto/
│ │ ├── WebhookRequest.java # Webhook request POJO
│ │ ├── WebhookResponse.java # Webhook response POJO
│ │ └── SubmissionRequest.java # Final submission request POJO
│ ├── service/
│ │ ├── WebhookService.java # Handles first API call
│ │ ├── SQLSolverService.java # SQL query solving logic
│ │ └── SubmissionService.java # Sends answer back to webhook
│ └── runner/
│ └── AppStartupRunner.java # Application startup flow runner
├── src/main/resources/
│ ├── application.properties # Spring Boot configurations
│ └── logback-spring.xml # Logging configuration (optional)
├── pom.xml # Maven dependencies
└── README.md # Project documentation


## Detailed File Descriptions

### Core Application Files
- **BajajFinservHealthAppApplication.java** - Main Spring Boot application class with `@SpringBootApplication`
- **AppStartupRunner.java** - Implements `CommandLineRunner` to execute the main flow at startup

### Configuration
- **WebClientConfig.java** - Configuration class for `WebClient` or `RestTemplate` beans
- **application.properties** - Application configuration (server port, endpoints, etc.)

### Data Transfer Objects (DTOs)
- **WebhookRequest.java** - POJO for incoming webhook request structure
- **WebhookResponse.java** - POJO for webhook response structure  
- **SubmissionRequest.java** - POJO for final answer submission

### Service Layer
- **WebhookService.java** - Service to call the initial webhook API
- **SQLSolverService.java** - Contains business logic to solve SQL queries
- **SubmissionService.java** - Service to submit the final answer back

### Build Configuration
- **pom.xml** - Maven dependencies including Spring Boot, testing, and other libraries

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
