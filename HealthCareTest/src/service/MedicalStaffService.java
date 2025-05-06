package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.MedicalStaff;

public class MedicalStaffService {

    static {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS MedicalStaff (
                    staffID INT PRIMARY KEY AUTO_INCREMENT,
                    username VARCHAR(50),
                    password VARCHAR(50),
                    fname VARCHAR(50),
                    lname VARCHAR(50),
                    createdAt INT
                );
            """;
            conn.createStatement().execute(sql);
            System.out.println(" MedicalStaff table ensured.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMedicalStaff(MedicalStaff staff) {
        String sql = "INSERT INTO MedicalStaff (username, password, fname, lname, createdAt) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, staff.getUsername());
            stmt.setString(2, staff.getPassword());
            stmt.setString(3, staff.getFname());
            stmt.setString(4, staff.getLname());
            stmt.setInt(5, staff.getCreatedAt());

            int rowsInserted = stmt.executeUpdate();
            System.out.println(" Rows inserted into MedicalStaff: " + rowsInserted);
        } catch (SQLException e) {
            System.err.println(" Failed to insert MedicalStaff:");
            e.printStackTrace();
        }
    }

    public List<MedicalStaff> getAllMedicalStaff() {
        List<MedicalStaff> list = new ArrayList<>();
        String sql = "SELECT * FROM MedicalStaff";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MedicalStaff staff = new MedicalStaff(
                    rs.getInt("staffID"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getInt("createdAt")
                );
                list.add(staff);
            }
            System.out.println(" MedicalStaff fetched: " + list.size());
        } catch (SQLException e) {
            System.err.println(" Failed to fetch MedicalStaff:");
            e.printStackTrace();
        }

        return list;
    }
}
