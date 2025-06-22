package com.example.shop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Accessories")
public class Accessories extends Product {
    private String material;
    private String color;


    public Accessories() {
        super();
    }


    public Accessories(int id, String title, String description, int price, String imageUrl, String brand, int stock, String material, String color) {
        super(id, title, description, price, imageUrl, brand, stock);
        this.material = material;
        this.color = color;
    }


    public String getMaterial() {
        return material;
    }
    public void setMaterial(String material) {
        this.material = material;
    }


    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}

