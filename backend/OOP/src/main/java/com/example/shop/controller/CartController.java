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

import java.util.Map;

@Controller
public class CartController extends BaseCartController {

    private final ProductRepository productRepo;

    public CartController(ProductRepository productRepo, Cart cart) {
        super(cart);
        this.productRepo = productRepo;
    }

    @GetMapping("/add-to-cart")
    public String addItemsToCart(HttpSession session) {
        // Tạo mẫu 4 sản phẩm
        Product product1 = new Product(5, "Áo thun nam", "Áo thun cotton", 100000, "link_image_1", "Brand A", 50);
        Product product2 = new Product(6, "Quần jeans", "Quần jeans đen", 250000, "link_image_2", "Brand B", 30);
        Product product3 = new Product(7, "Giày thể thao", "Giày thể thao trắng", 350000, "link_image_3", "Brand C", 20);
        Product product4 = new Product(8, "Túi xách nữ", "Túi xách da cao cấp", 500000, "link_image_4", "Brand D", 10);

        // Thêm sản phẩm vào giỏ hàng
        addToCart(session, new CartItem(product1, 1)); // Thêm 1 sản phẩm 1 vào giỏ
        addToCart(session, new CartItem(product2, 2)); // Thêm 2 sản phẩm 2 vào giỏ
        addToCart(session, new CartItem(product3, 1)); // Thêm 1 sản phẩm 3 vào giỏ
        addToCart(session, new CartItem(product4, 1)); // Thêm 1 sản phẩm 4 vào giỏ

        return "redirect:/cart"; // Sau khi thêm sản phẩm, chuyển đến trang giỏ hàng
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        Map<Long, CartItem> cartItems = getCartFromSession(session); // Lấy giỏ hàng từ session

        double total = cart.getTotal();
        model.addAttribute("cart", cartItems); // Thêm giỏ hàng vào model
        model.addAttribute("total", total);    // Thêm tổng vào model
        model.addAttribute("message", null);   // Thêm message nếu cần

        return "cart"; // Trả về trang giỏ hàng
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") Long productId, HttpSession session) {
        removeFromCart(session, productId); // Gọi phương thức xóa sản phẩm trong lớp cha
        return "redirect:/cart"; // Quay lại trang giỏ hàng sau khi xóa sản phẩm
    }

    @GetMapping("/back-to-home")
    public String backToHome() {
        return "redirect:/home"; // Điều hướng về trang chủ
    }
}

