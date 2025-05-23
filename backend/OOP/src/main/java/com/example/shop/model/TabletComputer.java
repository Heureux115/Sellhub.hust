package com.example.shop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TabletComputer")
public class TabletComputer extends Product {
    private String screenSize;        // Screen size (e.g., 10.5 inches)
    private String batteryLife;       // Battery life (e.g., 12 hours)
    private String storageCapacity;   // Storage capacity (e.g., 128GB)
    private String operatingSystem;   // Operating system (e.g., Android, iOS)

    // Default constructor
    public TabletComputer() {
        super();
    }

    // Constructor with parameters
    public TabletComputer(int id, String title, String description, int price, String imageUrl, String brand, int stock,
                          String screenSize, String batteryLife, String storageCapacity, String operatingSystem) {
        super(id, title, description, price, imageUrl, brand, stock); // Calls the parent Product constructor
        this.screenSize = screenSize;
        this.batteryLife = batteryLife;
        this.storageCapacity = storageCapacity;
        this.operatingSystem = operatingSystem;
    }

    // Getters and setters for the new fields
    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(String batteryLife) {
        this.batteryLife = batteryLife;
    }

    public String getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(String storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}

