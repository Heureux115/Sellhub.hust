package com.example.shop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Accessories")
public class Accessories extends Product {
    private String material; // Material of the accessory
    private String color;    // Color of the accessory

    // Default constructor
    public Accessories() {
        super();
    }

    // Constructor with parameters
    public Accessories(int id, String title, String description, double price, String imageUrl, String brand, int stock, String material, String color) {
        super(id, title, description, price, imageUrl, brand, stock); // Calls the constructor of the parent Product class
        this.material = material;
        this.color = color;
    }

    // Getter and Setter for material
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    // Getter and Setter for color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

