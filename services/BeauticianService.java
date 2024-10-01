// File: src/com/parlorstore/services/BeauticianService.java
package com.parlorstore.services;

import com.parlorstore.database.DatabaseHelper;
import com.parlorstore.models.Beautician;
import com.parlorstore.models.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BeauticianService {

    // Fetch all beauticians
    public List<Beautician> getAllBeauticians() {
        List<Beautician> beauticians = new ArrayList<>();
        String query = "SELECT * FROM beauticians";

        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Beautician beautician = new Beautician(
                        rs.getInt("beauticianId"),
                        rs.getString("name")
                );
                beauticians.add(beautician);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return beauticians;
    }

    // Fetch services for a specific beautician
    public List<Service> getServicesByBeauticianId(int beauticianId) {
        List<Service> services = new ArrayList<>();
        String query = "SELECT * FROM services WHERE beauticianId = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, beauticianId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
	                Service service = new Service(
	                        rs.getInt("serviceId"),
	                        rs.getInt("beauticianId"),
	                        rs.getString("serviceName"),
	                        rs.getDouble("price")
	                );
                services.add(service);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return services;
    }
}
