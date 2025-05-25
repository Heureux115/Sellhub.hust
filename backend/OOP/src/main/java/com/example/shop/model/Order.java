package com.example.shop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "[order]")
public class Order {

    public enum Status {
        DANG_GIAO, DA_GIAO, DA_HUY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Dùng List<OrderItems> để thay cho Map<Product, Integer>
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();

    private double total;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection
    @CollectionTable(name = "order_reviews", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "review")
    private Map<Product, String> reviews = new HashMap<>();

    public Order() {
        this.total = 0;
        this.orderDate = LocalDateTime.now();
        this.status = Status.DANG_GIAO;
    }

    public Order(User user, List<OrderItems> orderItems, double total) {
        this.user = user;
        this.orderItems = orderItems;
        this.total = total;
        this.orderDate = LocalDateTime.now();
        this.status = Status.DANG_GIAO;
        for (OrderItems item : orderItems) {
            item.setOrder(this);
        }
    }

    // Getters & setters ...

    public Integer getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Status getStatus() {
        return status;
    }

    public boolean cancelOrder() {
        if (status == Status.DANG_GIAO) {
            status = Status.DA_HUY;
            return true;
        }
        return false;
    }

    public boolean confirmReceived() {
        if (status == Status.DANG_GIAO) {
            status = Status.DA_GIAO;
            return true;
        }
        return false;
    }

    public boolean addReview(Product product, String review) {
        // Kiểm tra sản phẩm trong orderItems
        boolean productExists = orderItems.stream()
                .anyMatch(item -> item.getProduct().equals(product));

        if (status == Status.DA_GIAO && productExists) {
            reviews.put(product, review);
            return true;
        }
        return false;
    }

    public Map<Product, String> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderId).append("\n");
        sb.append("User: ").append(user.getUsername()).append("\n");
        sb.append("Date: ").append(orderDate).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Items:\n");
        for (OrderItems item : orderItems) {
            sb.append("- ").append(item.getProduct().getTitle())
                    .append(" x").append(item.getQuantity())
                    .append(" = $").append(item.getProduct().getPrice() * item.getQuantity()).append("\n");
        }
        sb.append("Total: $").append(total);
        return sb.toString();
    }
}


