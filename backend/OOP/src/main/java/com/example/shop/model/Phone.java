package com.example.shop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Phone")

public class Phone extends Product {
    private String batteryLife;       // Battery life in hours (e.g., 24 hours)
    private String cameraQuality;     // Camera quality (e.g., 12MP)
    private String screenSize;        // Screen size (e.g., 6.1 inches)
    private String operatingSystem;   // Operating system (e.g., Android, iOS)

    // Default constructor
    public Phone() {
        super();
    }

    // Constructor with parameters
    public Phone(int id, String title, String description, int price, String imageUrl, String brand, int stock,
                 String batteryLife, String cameraQuality, String screenSize, String operatingSystem) {
        super(id, title, description, price, imageUrl, brand, stock); // Calls the parent Product constructor
        this.batteryLife = batteryLife;
        this.cameraQuality = cameraQuality;
        this.screenSize = screenSize;
        this.operatingSystem = operatingSystem;
    }

    // Getters and setters for the new fields
    public String getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(String batteryLife) {
        this.batteryLife = batteryLife;
    }

    public String getCameraQuality() {
        return cameraQuality;
    }

    public void setCameraQuality(String cameraQuality) {
        this.cameraQuality = cameraQuality;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}

