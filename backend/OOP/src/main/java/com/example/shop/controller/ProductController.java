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

import java.util.Map;

@Controller
public class ProductController extends BaseCartController {

    private final ProductRepository productRepo;

    public ProductController(ProductRepository productRepo, Cart cart) {
        super(cart);
        this.productRepo = productRepo;
    }


    @PostMapping("/product/buyNow/{id}")
    public String buyNow(@PathVariable Long id, HttpSession session) {
        Product product = productRepo.findById(id).orElse(null);
        if (product != null) {
            clearCart(session);
            addToCart(session, new CartItem(product, 1));
            return "payment";
        }
        return "redirect:/home";
    }

    @PostMapping("/product/addToCart/{id}")
    public String addToCartHandler(@PathVariable Long id, HttpSession session) {
        productRepo.findById(id).ifPresent(product -> {
            addToCart(session, product); // gọi addToCart từ ProductController hoặc BaseCartController
        });
        return "redirect:/product/{id}";
    }

    private void addToCart(HttpSession session, Product product) {
        Map<Long, CartItem> cartItems = getCartFromSession(session);

        // Nếu sản phẩm đã tồn tại trong giỏ thì tăng số lượng, ngược lại thêm mới
        CartItem cartItem = cartItems.get(product.getId());
        if (cartItem == null) {
            cartItem = new CartItem(product, 1);
            cartItems.put(product.getId(), cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        // Cập nhật lại giỏ hàng trong session
        session.setAttribute("cart", cartItems);
    }



    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id).orElse(null);
        if (product != null) {
            model.addAttribute("product", product);
            return "product-detail";
        }
        return "redirect:/home";
    }
}

