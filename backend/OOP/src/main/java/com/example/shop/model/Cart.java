package com.example.shop.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Cart {
    private final Map<Long, CartItem> items = new HashMap<>();

    public void addItem(Product product, int quantity) {
        CartItem item = items.get(product.getId());
        if (item == null) {
            item = new CartItem(product, quantity);
            items.put(product.getId(), item);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }
    }

    public Map<Long, CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return items.values().stream().mapToDouble(CartItem::getTotal).sum();
    }

    public void clear() {
        items.clear();
    }

}

