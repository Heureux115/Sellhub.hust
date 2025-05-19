package com.example.shop.controller;

import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseCartController {

    @SuppressWarnings("unchecked")
    protected Map<Long, CartItem> getCartFromSession(HttpSession session) {
        Map<Long, CartItem> cartItems = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cartItems == null) {
            cartItems = new HashMap<>();
            session.setAttribute("cart", cartItems);
        }
        return cartItems;
    }

    protected void addToCart(HttpSession session, CartItem cartItem) {
        Map<Long, CartItem> cartItems = getCartFromSession(session);
        Long productId = cartItem.getProduct().getId();

        if (cartItems.containsKey(productId)) {
            CartItem existingItem = cartItems.get(productId);
            existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
        } else {
            cartItems.put(productId, cartItem);
        }

        session.setAttribute("cart", cartItems);
    }

    protected void removeFromCart(HttpSession session, Long productId) {
        Map<Long, CartItem> cartItems = getCartFromSession(session);
        cartItems.remove(productId);
        session.setAttribute("cart", cartItems);
    }

    protected void clearCart(HttpSession session) {
        session.setAttribute("cart", new HashMap<Long, CartItem>());
    }

    protected boolean isCartEmpty(HttpSession session) {
        Map<Long, CartItem> cartItems = getCartFromSession(session);
        return cartItems == null || cartItems.isEmpty();
    }

    protected void updateInventory(HttpSession session, ProductRepository productRepo) {
        Map<Long, CartItem> cartItems = getCartFromSession(session);
        for (CartItem item : cartItems.values()) {
            Product product = item.getProduct();
            if (product.getStock() >= item.getQuantity()) {
                product.setStock(product.getStock() - item.getQuantity());
                productRepo.save(product);
            }
        }
    }
}
