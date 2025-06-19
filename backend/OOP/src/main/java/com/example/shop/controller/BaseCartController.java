package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseCartController {
    protected final Cart cart;

    public BaseCartController(Cart cart) {
        this.cart = cart;
    }


    protected void updateInventory(Map<Long, CartItem> cartItems, ProductRepository productRepo) {
        for (CartItem item : cartItems.values()) {
            Product product = item.getProduct();
            if (product.getStock() >= item.getQuantity()) {
                product.setStock(product.getStock() - item.getQuantity());
                productRepo.save(product);
            }
        }
    }

    protected void clearCart(HttpSession session) {
        session.setAttribute("cart", new HashMap<Long, CartItem>());
    }


    protected boolean isCartEmpty(Cart cart) {
        return cart == null || cart.getItems().isEmpty();
    }



    protected Map<Long, CartItem> getCartFromSession(HttpSession session) {
        Map<Long, CartItem> cartItems = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cartItems == null) {
            cartItems = new HashMap<>();
            session.setAttribute("cart", cartItems); // <-- Cập nhật luôn nếu mới
        }
        return cartItems;
    }



    protected void addToCart(HttpSession session, CartItem cartItem) {
        Map<Long, CartItem> cartItems = getCartFromSession(session);
        cartItems.put(cartItem.getProduct().getId(), cartItem);
        session.setAttribute("cart", cartItems);
    }


    protected void removeFromCart(HttpSession session, Long productId) {
        Map<Long, CartItem> cartItems = getCartFromSession(session);
        CartItem item = cartItems.get(productId);
        if (item != null) {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
            } else {
                cartItems.remove(productId);
            }
        }
        session.setAttribute("cart", cartItems);
    }

}
