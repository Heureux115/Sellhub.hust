package com.example.shop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Phone")

public class Phone extends Product {
    private String batteryLife;
    private String cameraQuality;
    private String screenSize;
    private String operatingSystem;


    public Phone() {
        super();
    }


    public Phone(int id, String title, String description, int price, String imageUrl, String brand, int stock,
                 String batteryLife, String cameraQuality, String screenSize, String operatingSystem) {
        super(id, title, description, price, imageUrl, brand, stock); // Calls the parent Product constructor
        this.batteryLife = batteryLife;
        this.cameraQuality = cameraQuality;
        this.screenSize = screenSize;
        this.operatingSystem = operatingSystem;
    }


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

