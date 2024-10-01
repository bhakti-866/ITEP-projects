package com.parlorstore.services;

import com.parlorstore.database.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AppointmentService {

    // Book an appointment using serviceId
	public boolean bookAppointment(int userId, int beauticianId, int serviceId, String appointmentDate, String appointmentTime)	{
		String query = "INSERT INTO appointments (userId, beauticianId, serviceId, appointmentDate, appointmentTime) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the parameters for the appointment booking
            pstmt.setInt(1, userId);
            pstmt.setInt(2, beauticianId);
            pstmt.setInt(3, serviceId); // Use serviceId instead of serviceName
            pstmt.setString(4, appointmentDate);
            pstmt.setString(5, appointmentTime);

            // Execute the update and check if rows are affected
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

