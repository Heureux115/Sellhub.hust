package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController extends BaseCartController {

    private final ProductRepository productRepo;

    public PaymentController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        if (isCartEmpty(session)) {
            redirectAttributes.addFlashAttribute("message", "Không có gì trong giỏ hàng!");
            return "redirect:/cart";
        }

        updateInventory(session, productRepo);   // trừ số lượng tồn kho
        clearCart(session);                      // xóa giỏ hàng

        redirectAttributes.addFlashAttribute("message", "Thanh toán thành công!");
        return "redirect:/home";
    }
}

