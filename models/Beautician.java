package com.parlorstore.models;

public class Beautician {
    private int beauticianId;
    private String name;

    // Constructor, getters, and setters
    public Beautician(int beauticianId, String name) {
        this.beauticianId = beauticianId;
        this.name = name;
    }

    public int getBeauticianId() { return beauticianId; }
    public String getName() { return name; }
}
