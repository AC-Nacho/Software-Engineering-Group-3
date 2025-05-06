package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Patient;

public class PatientService {

    static {
        try (Connection conn = DatabaseUtil.getConnection()) { 
            String sql = """
                CREATE TABLE IF NOT EXISTS patients (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username TEXT,
                    password TEXT,
                    fname TEXT,
                    lname TEXT,
                    ssn TEXT,
                    dob DATE,
                    phoneNum TEXT,
                    address TEXT
                );
            """;
            conn.createStatement().execute(sql);
            System.out.println(" patients table ensured.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPatient(Patient patient) {
        String sql = "INSERT INTO patients (username, password, fname, lname, ssn, dob, phoneNum, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, patient.getUsername());
            stmt.setString(2, patient.getPassword());
            stmt.setString(3, patient.getFname());
            stmt.setString(4, patient.getLname());
            stmt.setString(5, patient.getSsn());
            stmt.setDate(6, patient.getDob());
            stmt.setString(7, patient.getPhoneNum());
            stmt.setString(8, patient.getAddress());

            int rowsInserted = stmt.executeUpdate();
            System.out.println(" Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            System.err.println(" Failed to insert patient:");
            e.printStackTrace();
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient patient = new Patient(
                    rs.getInt("PatientID"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getString("ssn"),
                    rs.getDate("dob"),
                    rs.getString("phoneNum"),
                    rs.getString("address")
                );
                list.add(patient);
            }
            System.out.println(" Patients fetched: " + list.size());
        } catch (SQLException e) {
            System.err.println(" Failed to fetch patients:");
            e.printStackTrace();
        }

        return list;
    }
}
