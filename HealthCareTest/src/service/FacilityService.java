package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Facility;

public class FacilityService {
    static {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS Facility (
                    facilityID INT PRIMARY KEY,
                    name VARCHAR(50),
                    address VARCHAR(100),
                    phoneNum VARCHAR(20)
                );
            """;
            conn.createStatement().execute(sql);
            System.out.println(" Facility table ensured.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFacility(Facility facility) {
        String sql = "INSERT INTO Facility (facilityID, name, address, phoneNum) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, facility.getFacilityID());
            stmt.setString(2, facility.getName());
            stmt.setString(3, facility.getAddress());
            stmt.setString(4, facility.getPhoneNum());

            stmt.executeUpdate();
            System.out.println(" Facility inserted.");
        } catch (SQLException e) {
            System.err.println(" Failed to insert Facility:");
            e.printStackTrace();
        }
    }

    public List<Facility> getAllFacilities() {
        List<Facility> list = new ArrayList<>();
        String sql = "SELECT * FROM Facility";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Facility facility = new Facility(
                    rs.getInt("facilityID"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phoneNum")
                );
                list.add(facility);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
