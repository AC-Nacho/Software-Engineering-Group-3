package service;

import java.sql.*;
import java.util.*;
import model.Appointment;

public class AppointmentService {

    static {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS appointments (
                    appointmentID INT AUTO_INCREMENT PRIMARY KEY,
                    doctorID INT,
                    patientID INT,
                    date VARCHAR(20),
                    time VARCHAR(20),
                    location VARCHAR(100)
                );
            """;
            conn.createStatement().execute(sql);
            System.out.println("Appointments table ensured.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAppointment(Appointment a) {
        String sql = "INSERT INTO appointments (doctorID, patientID, date, time, location) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getDoctorID());
            stmt.setInt(2, a.getPatientID());
            stmt.setString(3, a.getDate());
            stmt.setString(4, a.getTime());
            stmt.setString(5, a.getLocation());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Appointment a = new Appointment(
                    rs.getInt("appointmentID"),
                    rs.getInt("doctorID"),
                    rs.getInt("patientID"),
                    rs.getString("date"),
                    rs.getString("time"),
                    rs.getString("location")
                );
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
