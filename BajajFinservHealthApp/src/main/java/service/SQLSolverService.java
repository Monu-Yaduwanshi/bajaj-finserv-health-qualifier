package service;

import org.springframework.stereotype.Service;

@Service
public class SQLSolverService {

    public String solveSQLQuery(String regNo) {
        // ✅ Directly return solution for Question 2 (even RegNo)
        return "SELECT e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME, " +
               "COUNT(CASE WHEN e2.DOB > e.DOB THEN 1 END) AS YOUNGER_EMPLOYEES_COUNT " +
               "FROM EMPLOYEE e " +
               "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
               "LEFT JOIN EMPLOYEE e2 ON e.DEPARTMENT = e2.DEPARTMENT " +
               "GROUP BY e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME, e.DOB " +
               "ORDER BY e.EMP_ID DESC;";
    }
}
