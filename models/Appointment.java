package com.parlorstore.models;

public class Appointment {
    private int appointmentId;
    private int userId;
    private int beauticianId;
    private String service;

    // Constructor, getters, and setters
    public Appointment(int appointmentId, int userId, int beauticianId, String service) {
        this.appointmentId = appointmentId;
        this.userId = userId;
        this.beauticianId = beauticianId;
        this.service = service;
    }

    public int getAppointmentId() { return appointmentId; }
    public int getUserId() { return userId; }
    public int getBeauticianId() { return beauticianId; }
    public String getService() { return service; }
}
