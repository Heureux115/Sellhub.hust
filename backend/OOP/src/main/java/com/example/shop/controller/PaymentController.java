package com.example.shop.controller;

import com.example.shop.model.*;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PaymentController extends BaseCartController {

    private final ProductRepository productRepo;
    private final OrderService orderService;

    public PaymentController(ProductRepository productRepo, Cart cart, OrderService orderService) {
        super(cart);
        this.productRepo = productRepo;
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        Map<Long, CartItem> cartItems = (Map<Long, CartItem>) session.getAttribute("cart");

        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Bạn cần đăng nhập để thanh toán.");
            return "redirect:/login";
        }

        if (cartItems == null || cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Không có gì trong giỏ hàng!");
            return "redirect:/cart";
        }

        List<OrderItems> orderItemsList = new ArrayList<>();
        double total = 0;

        for (CartItem cartItem : cartItems.values()) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();
            double price = product.getPrice();

            // Tạo một OrderItems cho mỗi mục trong giỏ
            OrderItems orderItem = new OrderItems();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItemsList.add(orderItem);

            total += price * quantity;
        }

// Tạo Order (chuyển List vào, không dùng Map)
        Order order = new Order(user, orderItemsList, total);

// Gắn mối liên hệ giữa OrderItems và Order (nếu cần)
        for (OrderItems item : orderItemsList) {
            item.setOrder(order); // nếu trong OrderItems bạn có @ManyToOne Order
        }

// Lưu đơn hàng
        orderService.addOrder(order);


        updateInventory(cartItems, productRepo);  // updateInventory mới nhận Map chứ không phải Cart
        clearCart(session);                       // clearCart không cần cart đầu vào nữa

        redirectAttributes.addFlashAttribute("message", "Thanh toán thành công!");
        return "redirect:/home";
    }
}

