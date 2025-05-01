package com.example.shop.controller;

import org.springframework.ui.Model;
import com.example.shop.model.Cart;
import com.example.shop.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {

    private final ProductRepository productRepo;
    private final Cart cart;

    public CartController(ProductRepository productRepo, Cart cart) {
        this.productRepo = productRepo;
        this.cart = cart;
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        double total = cart.getTotal();
        model.addAttribute("cart", cart.getItems());
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/checkout")
    public String checkout(Model model) {
        double total = cart.getTotal();

        if (total == 0) {
            model.addAttribute("message", "Giỏ hàng trống. Vui lòng chọn sản phẩm để thanh toán.");
            return "cart";
        }
        cart.clear();

        model.addAttribute("message", "Thanh toán thành công!");
        return "checkout";
    }

    @GetMapping("/back-to-home")
    public String backToHome() {
        return "redirect:/";
    }
}

