package com.example.shop.controller;

import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.example.shop.model.Cart;
import com.example.shop.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {

    private final ProductRepository productRepo;
    private final Cart cart;


    public CartController(ProductRepository productRepo, Cart cart) {
        this.productRepo = productRepo;
        this.cart = cart;
    }

    @GetMapping("/add-to-cart")
    public String addItemsToCart(HttpSession session) {
        // Tạo mẫu 4 sản phẩm
        Product product1 = new Product(5, "Áo thun nam", "Áo thun cotton", 100000, "link_image_1", "Brand A", 50);
        Product product2 = new Product(6, "Quần jeans", "Quần jeans đen", 250000, "link_image_2", "Brand B", 30);
        Product product3 = new Product(7, "Giày thể thao", "Giày thể thao trắng", 350000, "link_image_3", "Brand C", 20);
        Product product4 = new Product(8, "Túi xách nữ", "Túi xách da cao cấp", 500000, "link_image_4", "Brand D", 10);

        // Thêm sản phẩm vào giỏ hàng (có thể thay đổi số lượng tùy ý)
        cart.addItem(product1, 1); // Thêm 1 sản phẩm 1 vào giỏ
        cart.addItem(product2, 2); // Thêm 2 sản phẩm 2 vào giỏ
        cart.addItem(product3, 1); // Thêm 1 sản phẩm 3 vào giỏ
        cart.addItem(product4, 1); // Thêm 1 sản phẩm 4 vào giỏ

        session.setAttribute("cart", cart.getItems());

        return "redirect:/cart"; // Sau khi thêm sản phẩm, chuyển đến trang giỏ hàng
    }

    @GetMapping("/cart")
    public String viewCart(Model model,HttpSession session) {
        Map<Long, CartItem> cartItems = (Map<Long, CartItem>) session.getAttribute("cart");

        if (cartItems == null) {
            cartItems = new HashMap<>(); // Nếu giỏ hàng không tồn tại, tạo mới
        }

        double total = cart.getTotal();
        System.out.println(cart.getItems());  // In ra dữ liệu cart để kiểm tra
        model.addAttribute("cart", cart.getItems());
        model.addAttribute("total", total);
        model.addAttribute("message", null);
        return "cart";
    }


    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") Long productId, HttpSession session) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");

        if (cart == null) {
            System.out.println("Giỏ hàng không tồn tại.");
            return "redirect:/cart"; // Điều hướng về lại trang giỏ hàng nếu giỏ hàng không tồn tại
        }

        if (cart != null) {
            System.out.println("Product ID cần xóa: " + productId);
            // In ra thông tin giỏ hàng trước khi xóa
            System.out.println("Giỏ hàng trước khi xóa sản phẩm:");
            for (Map.Entry<Long, CartItem> entry : cart.entrySet()) {
                System.out.println("Product ID: " + entry.getKey() + ", " + entry.getValue());
            }


            cart.remove(productId);

            // In ra giỏ hàng sau khi xóa
            System.out.println("Giỏ hàng sau khi xóa sản phẩm:");
            for (Map.Entry<Long, CartItem> entry : cart.entrySet()) {
                System.out.println("Product ID: " + entry.getKey() + ", " + entry.getValue());
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart"; // Điều hướng về lại trang giỏ hàng
    }


    @GetMapping("/back-to-home")
    public String backToHome() {
        return "redirect:/home";
    }
}

