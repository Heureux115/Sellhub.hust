package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public abstract class BaseCartController {
    protected final Cart cart;

    public BaseCartController(Cart cart) {
        this.cart = cart;
    }

    protected void addToCart(Product product) {
        cart.addItem(product, 1);
    }

    protected void clearCart() {
        cart.clear();
    }

    protected void updateInventory(Cart cart, ProductRepository productRepo) {
        for (CartItem item : cart.getItems().values()) {
            Product product = item.getProduct();
            if (product.getStock() >= item.getQuantity()) {
                product.setStock(product.getStock() - item.getQuantity());
                productRepo.save(product);
            }
        }
    }

    protected boolean isCartEmpty(Cart cart) {
        return cart == null || cart.getItems().isEmpty();
    }

    protected void clearCart(HttpSession session, Cart cart) {
        cart.clear();
        session.setAttribute("cart", cart);
    }

    @SuppressWarnings("unchecked")
    protected Map<Long, CartItem> getCartFromSession(HttpSession session) {
        Map<Long, CartItem> cartItems = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cartItems == null) {
            cartItems = cart.getItems(); // Giỏ hàng mới nếu chưa có trong session
        }
        return cartItems;
    }

    // Thêm sản phẩm vào giỏ
    protected void addToCart(HttpSession session, CartItem cartItem) {
        Map<Long, CartItem> cartItems = getCartFromSession(session);
        cartItems.put(cartItem.getProduct().getId(), cartItem); // Thêm sản phẩm vào giỏ
        session.setAttribute("cart", cartItems); // Cập nhật giỏ hàng trong session
    }

    // Xóa sản phẩm khỏi giỏ
    protected void removeFromCart(HttpSession session, Long productId) {
        Map<Long, CartItem> cartItems = getCartFromSession(session);
        cartItems.remove(productId); // Xóa sản phẩm khỏi giỏ
        session.setAttribute("cart", cartItems); // Cập nhật giỏ hàng trong session
    }

}
