package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController extends BaseCartController {

    private final ProductRepository productRepo;

    public ProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @PostMapping("/product/buyNow/{productId}")
    public String buyNow(@PathVariable Long productId, HttpSession session) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            clearCart(session);
            addToCart(session, new CartItem(product, 1));
            return "redirect:/checkout";
        }
        return "redirect:/home";
    }

    @PostMapping("/product/addToCart/{productId}")
    public String addToCartHandler(@PathVariable Long productId, HttpSession session) {
        productRepo.findById(productId).ifPresent(product ->
                addToCart(session, new CartItem(product, 1))
        );
        return "redirect:/cart";
    }


    @GetMapping("/product/{productId}")
    public String viewProduct(@PathVariable Long productId, Model model) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            model.addAttribute("product", product);
            return "redirect:/product-detail";
        }
        return "redirect:/home";
    }
}

