package com.example.shop.service;

import com.example.shop.model.Order;
import com.example.shop.model.OrderItems;
import com.example.shop.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final List<Order> orders = new ArrayList<>();

    public List<Order> getOrdersByUser(User user) {
        return orders.stream()
                .filter(o -> o.getUser().getUsername().equals(user.getUsername()))
                .collect(Collectors.toList());
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void cancelOrder(int orderId, User user) {
        orders.stream()
                .filter(o -> o.getOrderId() == orderId && o.getUser().equals(user))
                .findFirst()
                .ifPresent(Order::cancelOrder);
    }

    public void confirmOrder(int orderId, User user) {
        orders.stream()
                .filter(o -> o.getOrderId() == orderId && o.getUser().equals(user))
                .findFirst()
                .ifPresent(Order::confirmReceived);
    }

    public void addReview(int orderId, int productId, String review, User user) {
        orders.stream()
                .filter(o -> o.getOrderId() == orderId && o.getUser().equals(user))
                .findFirst()
                .ifPresent(order -> {
                    order.getOrderItems().stream()
                            .map(OrderItems::getProduct)
                            .filter(p -> p.getId() == productId)
                            .findFirst()
                            .ifPresent(p -> order.addReview(p, review));
                });
    }
}