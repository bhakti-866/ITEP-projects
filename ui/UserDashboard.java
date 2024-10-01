package com.parlorstore.ui;

import com.parlorstore.models.User;
import com.parlorstore.models.Beautician;
import com.parlorstore.models.Service;
import com.parlorstore.services.BeauticianService;
import com.parlorstore.services.AppointmentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserDashboard extends JFrame {
    private User user;
    private BeauticianService beauticianService;
    private AppointmentService appointmentService;

    private JComboBox<String> beauticianComboBox;
    private JList<String> servicesList;
    private DefaultListModel<String> servicesListModel;
    private JButton bookAppointmentButton;
    private JTextField appointmentDateField, appointmentTimeField;

    // To keep track of beautician IDs and services
    private List<Beautician> beauticians;
    private List<Service> services;

    // Constructor to initialize the UI components
    public UserDashboard(User user) {
        this.user = user;
        this.beauticianService = new BeauticianService();
        this.appointmentService = new AppointmentService();

        setTitle("User Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // Create background panel with an image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("C:\\Users\\Bhakti\\Downloads\\beautiful-young-woman-s-face-with-orchid-flower-in-a-spa-salon-cosmetic-skin-care-procedures-photo.jpeg"); // Specify your image path here
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(null);

        // Centralize UI components (position based on frame size)
        int centerX = getWidth() / 2 - 100;
        int centerY = 100;

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUserid() + "!");
        welcomeLabel.setBounds(centerX, centerY, 400, 30);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.black);
        backgroundPanel.add(welcomeLabel);

        // Beautician Selection
        JLabel beauticianLabel = new JLabel("Select Beautician:");
        beauticianLabel.setBounds(centerX - 150, centerY + 50, 150, 25);
        beauticianLabel.setFont(new Font("Arial", Font.BOLD, 20));
        beauticianLabel.setForeground(Color.black);
        backgroundPanel.add(beauticianLabel);

        beauticianComboBox = new JComboBox<>();
        beauticianComboBox.setBounds(centerX + 20, centerY + 50, 200, 25);
        beauticianComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        backgroundPanel.add(beauticianComboBox);

        // Populate beautician combo box
        beauticians = beauticianService.getAllBeauticians();
        for (Beautician beautician : beauticians) {
            beauticianComboBox.addItem(beautician.getName());
        }

        // Services List
        JLabel servicesLabel = new JLabel("Available Services:");
        servicesLabel.setBounds(centerX - 150, centerY + 100, 150, 25);
        servicesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        servicesLabel.setForeground(Color.black);
        backgroundPanel.add(servicesLabel);

        servicesListModel = new DefaultListModel<>();
        servicesList = new JList<>(servicesListModel);
        servicesList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane servicesScrollPane = new JScrollPane(servicesList);
        servicesScrollPane.setBounds(centerX, centerY + 100, 350, 150);
        backgroundPanel.add(servicesScrollPane);

        // Appointment Date Field
        JLabel dateLabel = new JLabel("Appointment Date (YYYY-MM-DD):");
        dateLabel.setBounds(centerX - 150, centerY + 270, 250, 25);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dateLabel.setForeground(Color.black);
        backgroundPanel.add(dateLabel);

        appointmentDateField = new JTextField();
        appointmentDateField.setBounds(centerX + 150, centerY + 270, 150, 25);
        backgroundPanel.add(appointmentDateField);

        // Appointment Time Field
        JLabel timeLabel = new JLabel("Appointment Time (HH:MM):");
        timeLabel.setBounds(centerX - 150, centerY + 310, 250, 25);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setForeground(Color.black);
        backgroundPanel.add(timeLabel);

        appointmentTimeField = new JTextField();
        appointmentTimeField.setBounds(centerX + 150, centerY + 310, 150, 25);
        backgroundPanel.add(appointmentTimeField);

        // Book Appointment Button
        bookAppointmentButton = new JButton("Book Appointment");
        bookAppointmentButton.setBounds(centerX, centerY + 350, 200, 30);
        bookAppointmentButton.setFont(new Font("Arial", Font.BOLD, 16));
        backgroundPanel.add(bookAppointmentButton);

        // Action Listener for Beautician Selection
        beauticianComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = beauticianComboBox.getSelectedIndex();
                if (selectedIndex >= 0) {
                    Beautician selectedBeautician = beauticians.get(selectedIndex);
                    loadServices(selectedBeautician.getBeauticianId());
                }
            }
        });

        // Action Listener for Booking Appointment
        bookAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int beauticianIndex = beauticianComboBox.getSelectedIndex();
                if (beauticianIndex < 0) {
                    JOptionPane.showMessageDialog(null, "Please select a beautician.");
                    return;
                }

                Beautician selectedBeautician = beauticians.get(beauticianIndex);
                String selectedService = servicesList.getSelectedValue();

                if (selectedService == null) {
                    JOptionPane.showMessageDialog(null, "Please select a service to book.");
                    return;
                }

                // Extract service name and price
                String[] serviceParts = selectedService.split(" - ");
                String serviceName = serviceParts[0];
                double price = Double.parseDouble(serviceParts[1].replace("$", ""));

                // Get the appointment date and time
                String appointmentDate = appointmentDateField.getText();
                String appointmentTime = appointmentTimeField.getText();

                if (appointmentDate.isEmpty() || appointmentTime.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both appointment date and time.");
                    return;
                }

                // Find the selected service ID
                Service service = services.stream()
                        .filter(s -> s.getServiceName().equals(serviceName))
                        .findFirst()
                        .orElse(null);

                if (service == null) {
                    JOptionPane.showMessageDialog(null, "Selected service is invalid.");
                    return;
                }

                // Book the appointment
                boolean success = appointmentService.bookAppointment(
                        user.getUserid(),  // Ensure this user ID exists in the users table
                        selectedBeautician.getBeauticianId(),
                        service.getServiceId(), // This should be a valid service ID
                        appointmentDate,
                        appointmentTime
                );

                if (success) {
                    JOptionPane.showMessageDialog(null, "Appointment booked successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to book appointment. Please try again.");
                }
            }
        });

        setVisible(true);
    }

    private void loadServices(int beauticianId) {
        servicesListModel.clear();
        services = beauticianService.getServicesByBeauticianId(beauticianId);
        for (Service service : services) {
            servicesListModel.addElement(service.getServiceName() + " - $" + service.getPrice());
        }
    }
}

