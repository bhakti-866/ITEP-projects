package com.parlorstore.services;

import com.parlorstore.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.parlorstore.database.DBConnection;

public class UserService {
    public boolean registerUser(String username, String password, String email) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username.trim());
            statement.setString(2, password.trim());
            statement.setString(3, email.trim());
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

//package com.parlorstore.services;
//
//import com.parlorstore.database.DatabaseHelper;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class UserService {
//
//    // Method to register a new user
//    public boolean registerUser(String username, String password, String email) {
//        String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
//
//        try (Connection conn = DatabaseHelper.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            // Set the parameters for the query
//            pstmt.setString(1, username.trim());
//            pstmt.setString(2, password.trim());
//            pstmt.setString(3, email.trim());
//
//            // Execute the update
//            int affectedRows = pstmt.executeUpdate();
//
//            // If one row is affected, the registration is successful
//            return affectedRows > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false; // Return false if an error occurs
//    }
//}
