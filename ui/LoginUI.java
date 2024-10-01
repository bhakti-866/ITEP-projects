package com.parlorstore.ui;

import com.parlorstore.database.DBConnection;
import com.parlorstore.models.User;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton; // New register button
    private DBConnection conn;

    public LoginUI() {
        setTitle("Parlor Store Login");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon background = new ImageIcon("C:\\Users\\Bhakti\\OneDrive\\Desktop\\Core Java\\ParlorStoreManagement\\img\\loginpg.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        setContentPane(backgroundPanel);  // Set this panel as the content pane
        backgroundPanel.setLayout(null);  // Keep the null layout for custom component positioning

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(350, 150, 100, 30);
        usernameLabel.setForeground(Color.black);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(500, 150, 150, 30);
        usernameField.setFont(new Font("Arial", Font.BOLD, 15));
        add(usernameField);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(350, 200, 100, 30);
        passwordLabel.setForeground(Color.black);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(500, 200, 150, 30);
        passwordField.setFont(new Font("Arial", Font.BOLD, 15));
        add(passwordField);

        // Login and Register buttons
        loginButton = new JButton("Login");
        loginButton.setBounds(500, 250, 100, 30);
        loginButton.setForeground(Color.black);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(600, 250, 100, 30);
        registerButton.setForeground(Color.black);
        registerButton.setFont(new Font("Arial", Font.BOLD, 15));
        add(registerButton);

        // Register button action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationUI();  // Open the registration page
                dispose(); // Close the login page
            }
        });

        setVisible(true);

        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Validate login credentials from the database
                login(username, password);
            }
        });
    }

    // Method to validate user credentials
    private void login(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Establish connection
            conn = DBConnection.getConnection();

            // SQL query to check username and password
            String sql = "SELECT userId, email FROM users WHERE username = ? AND password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            // Execute query and get result
            rs = ps.executeQuery();

            if (rs.next()) {
                // If user exists, retrieve details
                int userId = rs.getInt("userId");
                String email = rs.getString("email");

                // Create a User object and open UserDashboard
                User user = new User(userId, username, password, email);
                new UserDashboard(user).setVisible(true);
                dispose();  // Close the login screen
            } else {
                // Invalid credentials
                JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error. Please try again later.");
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    String regex2 = "^(?=.*[0-9])"
            + "(?=.[a-z])(?=.[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";
    
    
    
}

