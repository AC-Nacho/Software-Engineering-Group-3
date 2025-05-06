package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Department;

public class DepartmentService {

    static {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS departments (
                    depID INT PRIMARY KEY,
                    name VARCHAR(50)
                );
            """;
            conn.createStatement().execute(sql);
            System.out.println(" departments table ensured.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDepartment(Department department) {
        String sql = "INSERT INTO departments (depID, name) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, department.getDepID());
            stmt.setString(2, department.getName());

            int rowsInserted = stmt.executeUpdate();
            System.out.println(" Department inserted: " + rowsInserted);
        } catch (SQLException e) {
            System.err.println(" Failed to insert department:");
            e.printStackTrace();
        }
    }

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Department department = new Department(
                    rs.getInt("depID"),
                    rs.getString("name")
                );
                list.add(department);
            }
            System.out.println(" Departments fetched: " + list.size());
        } catch (SQLException e) {
            System.err.println(" Failed to fetch departments:");
            e.printStackTrace();
        }

        return list;
    }
}
