package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.CartItem;
import com.example.shop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class PaymentController extends BaseCartController {

    private final ProductRepository productRepo;

    public PaymentController(ProductRepository productRepo, Cart cart) {
        super(cart);
        this.productRepo = productRepo;
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        Map<Long, CartItem> cartItems = (Map<Long, CartItem>) session.getAttribute("cart");


        if (cartItems == null || cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Không có gì trong giỏ hàng!");
            return "redirect:/cart";
        }


        updateInventory(cartItems, productRepo);  // updateInventory mới nhận Map chứ không phải Cart
        clearCart(session);                       // clearCart không cần cart đầu vào nữa

        redirectAttributes.addFlashAttribute("message", "Thanh toán thành công!");
        return "redirect:/home";
    }
}

