package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private final ProductRepository productRepo;
    private final Cart cart;

    public ProductController(ProductRepository productRepo, Cart cart) {
        this.productRepo = productRepo;
        this.cart = cart;
    }

    @PostMapping("/product/buyNow/{productId}")
    public String buyNow(@PathVariable Long productId) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            cart.clear();
            cart.addItem(product, 1);
            return "redirect:/checkout";
        }
        return "redirect:/home";
    }

    @PostMapping("/product/addToCart/{productId}")
    public String addToCart(@PathVariable Long productId) {
        productRepo.findById(productId).ifPresent(product -> cart.addItem(product, 1));
        return "redirect:/cart";
    }

    @GetMapping("/product/{productId}")
    public String viewProduct(@PathVariable Long productId, Model model) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            model.addAttribute("product", product);
            return "product-detail";
        }
        return "redirect:/home";
}
}
