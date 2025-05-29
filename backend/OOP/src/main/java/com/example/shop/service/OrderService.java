package com.example.shop.service;

import com.example.shop.model.CartItem;
import com.example.shop.model.Order;
import com.example.shop.model.OrderItems;
import com.example.shop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.shop.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepo.findByUser(user);
    }

    public void addOrder(Order order) {
        orderRepo.save(order); // Lưu vào DB
    }

    public void cancelOrder(int orderId, User user) {
        Order order = orderRepo.findByorderIdAndUser(orderId, user);
        if (order != null) {
            order.cancelOrder(); // logic business
            orderRepo.save(order); // cập nhật lại DB
        }
    }

    public void confirmOrder(int orderId, User user) {
        Order order = orderRepo.findByorderIdAndUser(orderId, user);
        if (order != null) {
            order.confirmReceived();
            orderRepo.save(order);
        }
    }

    public void addReview(int orderId, int productId, String review, User user) {
        Order order = orderRepo.findByorderIdAndUser(orderId, user);
        if (order != null) {
            order.getOrderItems().stream()
                    .filter(item -> item.getProduct().getId() == productId)
                    .findFirst()
                    .ifPresent(item -> order.addReview(item.getProduct(), review));
            orderRepo.save(order);
        }
    }

    public void createOrder(User user, Map<Long, CartItem> cartItems) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.Status.DANG_GIAO);

        List<OrderItems> items = cartItems.values().stream().map(cartItem -> {
            OrderItems item = new OrderItems();
            item.setOrder(order);
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            return item;
        }).collect(Collectors.toList());

        order.setOrderItems(items);

        orderRepo.save(order); // Lưu cả order + items (cascade)
    }
}
