package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import org.springframework.web.bind.annotation.PostMapping;

public class PaymentController {

    private final ProductRepository productRepo;
    private final Cart cart;

    public PaymentController(ProductRepository productRepo, Cart cart) {
        this.productRepo = productRepo;
        this.cart = cart;
    }

    @PostMapping("/checkout")
    public String checkout() {
        for (CartItem item : cart.getItems().values()) {
            Product product = item.getProduct();
            if (product.getStock() > 0) {
                product.setStock(product.getStock() - item.getQuantity());
                productRepo.save(product);
            }
        }
        cart.clear();
        return "redirect:/home";
    }

}
