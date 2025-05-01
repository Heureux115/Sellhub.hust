package com.example.shop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private final List<Product> products = new ArrayList<>();
    private final Map<String, User> users = new HashMap<>();

    public Store() {
        // Thêm sản phẩm mẫu
        //products.add(new Product(1, "Áo Thun", 150000));
        //products.add(new Product(2, "Quần Jeans", 350000));
        //products.add(new Product(3, "Giày Thể Thao", 750000));
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean registerUser(String username, String password, String email, String phone) {
        if(users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password,email, phone));
        return true;
    }

    public User loginUser(String username, String password) {
        User user = users.get(username);
        if(user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
    }
}
