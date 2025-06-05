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
    public String buyNow(@PathVariable Long id, HttpSession session, Model model) {
        Product product = productRepo.findById(id).orElse(null);
        User user = (User) session.getAttribute("user");
        if (user == null){
            return "login";
        }
        if (product != null && product.getId() != null) {
            clearCart(session);
            addToCart(session, new CartItem(product, 1));
            model.addAttribute("cart", getCartFromSession(session));
            return "payment";
        }else {
            throw new IllegalArgumentException("Product or Product ID is null");
        }
    }

    @PostMapping("/product/addToCart/{id}")
    public String addToCartHandler(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null){
            return "login";
        }
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
