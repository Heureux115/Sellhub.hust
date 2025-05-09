package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController {

    private final ProductRepository productRepo;

    public PaymentController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }


    @PostMapping("/checkout")
    public String checkout(HttpSession session,RedirectAttributes redirectAttributes) {
        // Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("cart");
        System.out.println("Product ID cần xóa: ");
        // Kiểm tra nếu giỏ hàng không tồn tại
        if (cart == null || cart.getItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Không có gì trong giỏ hàng!");
            return "redirect:/cart";
        }

        // Duyệt qua các item trong giỏ hàng để giảm số lượng hàng tồn kho
        for (CartItem item : cart.getItems().values()) {
            Product product = item.getProduct();
            if (product.getStock() > 0) {
                // Giảm số lượng tồn kho
                product.setStock(product.getStock() - item.getQuantity());
                productRepo.save(product);  // Lưu lại thay đổi vào cơ sở dữ liệu
            }
        }

        // Sau khi thanh toán thành công, xóa giỏ hàng
        cart.clear();

        // Cập nhật lại giỏ hàng trong session (nếu cần)
        session.setAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("message", "Thanh toán thành công!");
        return "redirect:/home";  // Chuyển đến trang chủ sau khi thanh toán thành công
    }

}
