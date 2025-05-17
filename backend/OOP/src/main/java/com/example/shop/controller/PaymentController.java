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

    public PaymentController(ProductRepository productRepo, Cart cart) {
        super(cart);
        this.productRepo = productRepo;
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");

        if (isCartEmpty(cart)) {
            redirectAttributes.addFlashAttribute("message", "Không có gì trong giỏ hàng!");
            return "redirect:/cart";
        }

        updateInventory(cart, productRepo);  // kế thừa từ lớp cha
        clearCart(session, cart);           // kế thừa từ lớp cha

        redirectAttributes.addFlashAttribute("message", "Thanh toán thành công!");
        return "redirect:/home";
    }
}

