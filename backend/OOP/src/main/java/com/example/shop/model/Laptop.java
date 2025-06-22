package com.example.shop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Laptop")
public class Laptop extends Product {
    private String processor;
    private String ram;
    private String screenSize;
    private String storage;

    // Default constructor
    public Laptop() {
        super();
    }

    // Constructor with parameters
    public Laptop(int id, String title, String description, int price, String imageUrl, String brand, int stock,
                  String processor, String ram, String screenSize, String storage) {
        super(id, title, description, price, imageUrl, brand, stock); // Calls the constructor of the parent Product class
        this.processor = processor;
        this.ram = ram;
        this.screenSize = screenSize;
        this.storage = storage;
    }


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

