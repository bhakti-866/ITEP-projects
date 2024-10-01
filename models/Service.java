package com.parlorstore.models;

public class Service {
    private int serviceId;
    private int beauticianId;
    private String serviceName;
    private double price;

    // Constructor, getters, and setters
    public Service(int serviceId, int beauticianId, String serviceName, double price) {
        this.serviceId = serviceId;
        this.beauticianId = beauticianId;
        this.serviceName = serviceName;
        this.price = price;
    }

    public int getServiceId() { return serviceId; }
    public int getBeauticianId() { return beauticianId; }
    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }
}
