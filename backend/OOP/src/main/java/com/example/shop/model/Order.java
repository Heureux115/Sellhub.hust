package com.example.shop.model;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "orders")
public class Order {
    private static long orderCounter = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // hoặc AUTO
    private Long orderId;

    @ManyToOne
    private User user;

    @Transient // Vì JPA không map Map<Product, Integer> tự động được
    private Map<Product, Integer> items;

    private double total;

    protected Order() {
        this.orderId = orderCounter++;
        this.items = new HashMap<>();
    }

    public Order(User user, Map<Product, Integer> items, double total) {
        this.orderId = orderCounter++;
        this.user = user;
        this.items = new HashMap<>(items);
        this.total = total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderId).append("\n");
        sb.append("User: ").append(user.getUsername()).append("\n");
        sb.append("Items:\n");
        for (var entry : items.entrySet()) {
            sb.append("- ").append(entry.getKey().getTitle())
                    .append(" x").append(entry.getValue())
                    .append(" = $").append(entry.getKey().getPrice() * entry.getValue()).append("\n");
        }
        sb.append("Total: $").append(total);
        return sb.toString();
    }
}

