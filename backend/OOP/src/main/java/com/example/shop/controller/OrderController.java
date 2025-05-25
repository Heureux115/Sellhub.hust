package com.example.shop.controller;

import com.example.shop.model.Order;
import com.example.shop.model.User;
import com.example.shop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService; // service quản lý danh sách Order

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/my-orders")
    public String userOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Order> orders = orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam int orderId, HttpSession session) {
        orderService.cancelOrder(orderId, (User) session.getAttribute("user"));
        return "redirect:/order/my-orders";
    }

    @PostMapping("/confirm")
    public String confirmReceived(@RequestParam int orderId, HttpSession session) {
        orderService.confirmOrder(orderId, (User) session.getAttribute("user"));
        return "redirect:/order/my-orders";
    }

    @PostMapping("/review")
    public String reviewProduct(@RequestParam int orderId,
                                @RequestParam int productId,
                                @RequestParam String review,
                                HttpSession session) {
        orderService.addReview(orderId, productId, review, (User) session.getAttribute("user"));
        return "redirect:/order/my-orders";
    }
}
