package com.example.shop.controller;

import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.example.shop.model.Cart;
import com.example.shop.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class CartController extends BaseCartController {

    private final ProductRepository productRepo;

    public CartController(ProductRepository productRepo, Cart cart) {
        super(cart);
        this.productRepo = productRepo;
    }


    private void prepareCartModel(Model model, HttpSession session) {
        Map<Long, CartItem> cartItems = getCartFromSession(session);
        double total = cartItems.values().stream()
                .mapToDouble(CartItem::getTotal)
                .sum();
        model.addAttribute("cart", cartItems);
        model.addAttribute("total", total);
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        prepareCartModel(model, session);
        return "cart";
    }

    @GetMapping("/payment")
    public String viewPayment(Model model, HttpSession session) {
        prepareCartModel(model, session);
        return "payment";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") Long productId, HttpSession session) {
        removeFromCart(session, productId); // Gọi phương thức xóa sản phẩm trong lớp cha
        return "redirect:/cart"; // Quay lại trang giỏ hàng sau khi xóa sản phẩm
    }

    @GetMapping("/back-to-home")
    public String backToHome() {
        return "redirect:/home"; // Điều hướng về trang chủ
    }
}

