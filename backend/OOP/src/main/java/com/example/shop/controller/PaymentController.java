package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.User;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class PaymentController extends BaseCartController {

    private final ProductRepository productRepo;

    private final OrderService orderService;

    public PaymentController(ProductRepository productRepo, OrderService orderService, Cart cart) {
        super(cart);
        this.productRepo = productRepo;
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        Map<Long, CartItem> cartItems = (Map<Long, CartItem>) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if (cartItems == null || cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Không có gì trong giỏ hàng!");
            return "redirect:/cart";
        }

        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Bạn cần đăng nhập để thanh toán.");
            return "redirect:/login";
        }

        // 1. Trừ số lượng tồn kho
        updateInventory(cartItems, productRepo);

        // 2. Lưu đơn hàng vào DB
        orderService.createOrder(user, cartItems);

        // 3. Xóa giỏ hàng
        clearCart(session);

        redirectAttributes.addFlashAttribute("message", "Thanh toán thành công!");
        return "redirect:/home";
    }
}

