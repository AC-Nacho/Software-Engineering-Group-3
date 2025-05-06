package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Doctor;

public class DoctorService {

    static {
        try (Connection conn = DatabaseUtil.getConnection()) { // <== FIXED: use DatabaseUtil
            String sql = """
                CREATE TABLE IF NOT EXISTS patients (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username TEXT,
                    fname TEXT,
                    lname TEXT,
                    phoneNum TEXT,
                    facility TEXT
                );
            """;
            conn.createStatement().execute(sql);
            System.out.println(" Doctors table ensured.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (username, fname, lname, phoneNum, specialty) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctor.getUsername());
            stmt.setString(2, doctor.getFname());
            stmt.setString(3, doctor.getLname());
            stmt.setString(4, doctor.getPhoneNum());
            stmt.setString(5, doctor.getSpecialty());

            int rowsInserted = stmt.executeUpdate();
            System.out.println(" Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            System.err.println(" Failed to insert Doctor:");
            e.printStackTrace();
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Doctor doctor = new Doctor(
                    rs.getInt("DoctorID"),
                    rs.getString("username"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getString("phoneNum"),
                    rs.getString("specialty")
                );
                list.add(doctor);
            }
            System.out.println(" Doctors fetched: " + list.size());
        } catch (SQLException e) {
            System.err.println(" Failed to fetch doctors:");
            e.printStackTrace();
        }

        return list;
    }
}
