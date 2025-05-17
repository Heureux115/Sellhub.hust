package com.example.shop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Laptop")
public class Laptop extends Product {
    private String processor;   // Type of processor (e.g., Intel i7, AMD Ryzen 5)
    private String ram;         // Amount of RAM (e.g., 16GB)
    private String screenSize;  // Screen size (e.g., 15.6 inches)
    private String storage;     // Storage capacity (e.g., 512GB SSD)

    // Default constructor
    public Laptop() {
        super();
    }

    // Constructor with parameters
    public Laptop(int id, String title, String description, double price, String imageUrl, String brand, int stock,
                  String processor, String ram, String screenSize, String storage) {
        super(id, title, description, price, imageUrl, brand, stock); // Calls the constructor of the parent Product class
        this.processor = processor;
        this.ram = ram;
        this.screenSize = screenSize;
        this.storage = storage;
    }

    // Getters and setters for the new fields
    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }
}

