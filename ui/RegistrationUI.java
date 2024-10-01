package com.parlorstore.ui;

import com.parlorstore.services.UserService;

import javax.swing.*;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JButton registerButton;
    private JLabel statusLabel;

    public RegistrationUI() {
        // Setup the JFrame layout
        setTitle("Register");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon background = new ImageIcon("C:\\Users\\Bhakti\\Downloads\\7b26dabcd682dc43084371cedb07cbe4.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        setContentPane(backgroundPanel);  // Set this panel as the content pane
        backgroundPanel.setLayout(null);  // Keep the null layout for custom component positioning

        


        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 30, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 30, 150, 25);
        add(usernameField);

        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 70, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 70, 150, 25);
        add(emailField);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 150, 25);
        add(passwordField);

        // Confirm password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 150, 100, 25);
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(150, 150, 150, 25);
        add(confirmPasswordField);

        // Register button
        registerButton = new JButton("Register");
        registerButton.setBounds(110, 190, 100, 30);
        add(registerButton);
        
        setVisible(true);

        // Status label for feedback
        statusLabel = new JLabel("");
        statusLabel.setBounds(50, 230, 250, 25);
        add(statusLabel);

        // Action listener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });
    }

    // Method to handle the registration process
    private void performRegistration() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        String regex="^(.+)@(.+)&";
        Pattern p1=Pattern.compile(regex);
        Matcher m1=p1.matcher(email);
        if(m1.matches()==false)
        {
        	JFrame f= new JFrame();
        	JOptionPane.showMessageDialog(f, "Invaild Email");
        }
        
        //password check
        String regex2 = "^(?=.*[0-9])"
                + "(?=.[a-z])(?=.[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        
        Pattern p= Pattern.compile(regex2);
		Matcher m=p.matcher(password);
		//System.out.println(m.matches());
		if(m.matches()==false) {
			JFrame f=new JFrame();
			JOptionPane.showMessageDialog(f,"Invalid password");
		}
        
        

        // Validate if both passwords match
        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match.");
            return;
        }

        // Call the UserService to register the user
        UserService userService = new UserService();
        boolean isRegistered = userService.registerUser(username, password, email);

        if (isRegistered) {
            statusLabel.setText("Registration successful!");
            
            // Redirect to LoginUI after successful registration
            new LoginUI().setVisible(true);
            dispose(); // Close the registration window
            
        } else {
            statusLabel.setText("Registration failed.");
        }
    }
}
