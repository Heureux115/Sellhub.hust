package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.aspectj.apache.bcel.generic.ClassGen;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class ProductController extends BaseCartController {

    private final ProductRepository productRepo;

    public ProductController(ProductRepository productRepo, Cart cart) {
        super(cart);
        this.productRepo = productRepo;
    }


    @PostMapping("/product/buyNow/{id}")
    public String buyNow(@PathVariable Long id,
                         @org.springframework.web.bind.annotation.RequestParam int quantity,
                         HttpSession session,
                         Model model) {
        Product product = productRepo.findById(id).orElse(null);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "login";
        }

        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Product or Product ID is null");
        }

        if (quantity < 1) {
            quantity = 1;
        }

        if (quantity > product.getStock()) {
            quantity = product.getStock();
        }

        clearCart(session);
        addToCart(session, product, quantity);
        model.addAttribute("cart", getCartFromSession(session));
        return "redirect:/payment";
    }


    @PostMapping("/product/addToCart/{id}")
    public String addToCartHandler(@PathVariable Long id,
                                   @org.springframework.web.bind.annotation.RequestParam int quantity,
                                   HttpSession session) {
        System.out.println("=== ADD TO CART ===");
        System.out.println("Quantity nhận từ request = " + quantity);

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }

        productRepo.findById(id).ifPresent(product -> {
            addToCart(session, product, quantity);
        });

        Map<Long, CartItem> cartItems = getCartFromSession(session);
        CartItem item = cartItems.get(id);
        if (item != null) {
            System.out.println("Quantity trong session sau khi add = " + item.getQuantity());
        }

        return "redirect:/product/" + id;
    }
    private void addToCart(HttpSession session, Product product, int quantity) {
        Map<Long, CartItem> cartItems = getCartFromSession(session);

        if (quantity < 1) {
            quantity = 1;
        }

        if (quantity > product.getStock()) {
            quantity = product.getStock();
        }

        CartItem cartItem = cartItems.get(product.getId());

        if (cartItem == null) {
            cartItem = new CartItem(product, quantity);
            cartItems.put(product.getId(), cartItem);
        } else {
            int newQuantity = cartItem.getQuantity() + quantity;
            if (newQuantity > product.getStock()) {
                newQuantity = product.getStock();
            }
            cartItem.setQuantity(newQuantity);
        }

        session.setAttribute("cart", cartItems);
    }


    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable Long id, Model model, HttpSession session) {
        Product product = productRepo.findById(id).orElse(null);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        if (product != null) {
            model.addAttribute("product", product);
            return "product-detail";
        }
        return "redirect:/home";
    }
}
