# Bajaj Finserv Health App (Qualifier 1)

This Spring Boot application:
- Generates a webhook on startup
- Solves SQL Question 2 (younger employee count)
- Posts the solution (final SQL query) to the webhook using JWT Authorization.

BajajFinservHealthApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bajajfinserv/
│   │   │           └── health/
│   │   │               ├── BajajFinservHealthAppApplication.java    # Main Spring Boot application class
│   │   │               ├── config/
│   │   │               │   ├── WebClientConfig.java                 # WebClient configuration
│   │   │               │   └── AppConfig.java                       # Additional application configurations
│   │   │               ├── controller/
│   │   │               │   ├── WebhookController.java               # REST controller for webhook endpoints
│   │   │               │   └── HealthController.java                # Health check and monitoring endpoints
│   │   │               ├── dto/
│   │   │               │   ├── WebhookRequest.java                  # Request body POJO for webhook
│   │   │               │   ├── WebhookResponse.java                 # Response POJO for webhook
│   │   │               │   ├── SubmissionRequest.java               # Final submission request POJO
│   │   │               │   └── ApiResponse.java                     # Standard API response format
│   │   │               ├── service/
│   │   │               │   ├── WebhookService.java                  # Handles calling first API
│   │   │               │   ├── SQLSolverService.java                # Solves SQL query logic
│   │   │               │   ├── SubmissionService.java               # Sends answer back to webhook
│   │   │               │   └── ValidationService.java               # Request validation service
│   │   │               ├── repository/
│   │   │               │   └── QueryRepository.java                 # Data access layer (if needed)
│   │   │               ├── exception/
│   │   │               │   ├── GlobalExceptionHandler.java          # Global exception handling
│   │   │               │   ├── ApiException.java                    # Custom API exception
│   │   │               │   └── ErrorResponse.java                   # Error response structure
│   │   │               ├── util/
│   │   │               │   ├── Constants.java                       # Application constants
│   │   │               │   ├── SQLParser.java                       # SQL parsing utilities
│   │   │               │   └── ResponseBuilder.java                 # Response building utilities
│   │   │               └── runner/
│   │   │                   └── AppStartupRunner.java                # Runs flow at startup (if needed)
│   │   └── resources/
│   │       ├── application.properties                               # Main application configuration
│   │       ├── application-dev.properties                           # Development environment configuration
│   │       ├── application-prod.properties                          # Production environment configuration
│   │       ├── logback-spring.xml                                   # Logging configuration
│   │       └── data/                                                # Sample data files (if any)
│   └── test/
│       └── java/
│           └── com/
│               └── bajajfinserv/
│                   └── health/
│                       ├── BajajFinservHealthAppApplicationTests.java  # Main application tests
│                       ├── controller/
│                       │   └── WebhookControllerTest.java              # Controller tests
│                       ├── service/
│                       │   ├── WebhookServiceTest.java                 # Service tests
│                       │   ├── SQLSolverServiceTest.java               # SQL solver tests
│                       │   └── SubmissionServiceTest.java              # Submission service tests
│                       └── util/
│                           └── SQLParserTest.java                      # Utility tests
├── .gitignore                                                       # Git ignore rules
├── pom.xml                                                          # Maven dependencies and build config
├── README.md                                                        # Project documentation
├── LICENSE                                                          # Project license
└── Dockerfile                                                       # Docker containerization

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
