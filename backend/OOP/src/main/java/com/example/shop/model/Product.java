package com.example.shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "[product]")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int price;
    private String imageUrl;
    private String brand;
    private int stock;
    @Column(name = "product_type", insertable = false, updatable = false)
    private String category;


    public Product(int id, String title,String description ,int price, String imageUrl,String brand, int stock) {
        this.id = (long) id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.stock = stock;
        this.category = category;
    }

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrand() { return brand; }

    public void setBrand(String brand) { this.brand = brand; }

    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }

    public String getCategory() {return category;}

    public void setCategory(String category) { this.category = category; }
}
