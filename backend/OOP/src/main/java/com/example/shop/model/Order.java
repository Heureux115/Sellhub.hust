package com.example.shop.model;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private static int orderCounter = 1;
    private final int orderId;
    private final User user;
    private final Map<Product, Integer> items;
    private final double total;

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

