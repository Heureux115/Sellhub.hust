package com.example.shop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SecondHand")
public class SecondHand extends Product {
    private String condition;        // Condition of the second-hand product (e.g., "Good", "Like New", "Fair")
    private String previousOwner;    // Previous owner of the item (optional)
    private boolean hasOriginalPackaging; // Whether the product comes with the original packaging

    // Default constructor
    public SecondHand() {
        super();
    }

    // Constructor with parameters
    public SecondHand(int id, String title, String description, double price, String imageUrl, String brand, int stock,
                      String condition, String previousOwner, boolean hasOriginalPackaging) {
        super(id, title, description, price, imageUrl, brand, stock); // Calls the parent Product constructor
        this.condition = condition;
        this.previousOwner = previousOwner;
        this.hasOriginalPackaging = hasOriginalPackaging;
    }

    // Getters and setters for the new fields
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPreviousOwner() {
        return previousOwner;
    }

    public void setPreviousOwner(String previousOwner) {
        this.previousOwner = previousOwner;
    }

    public boolean isHasOriginalPackaging() {
        return hasOriginalPackaging;
    }

    public void setHasOriginalPackaging(boolean hasOriginalPackaging) {
        this.hasOriginalPackaging = hasOriginalPackaging;
    }
}

