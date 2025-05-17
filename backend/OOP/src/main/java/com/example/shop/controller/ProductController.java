package com.example.shop.controller;

import com.example.shop.model.Cart;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController extends BaseCartController {

    private final ProductRepository productRepo;

    public ProductController(ProductRepository productRepo, Cart cart) {
        super(cart);
        this.productRepo = productRepo;
    }

    public void init() {
        Product product1 = new Product(1,"Áo thun nam", "Áo thun cotton, thoáng mát", 150000, "https://link-to-image.com/ao-thun.jpg", "Nike", 100);
        Product product2 = new Product(2,"Giày thể thao", "Giày thể thao chạy bộ, bền bỉ", 350000, "https://link-to-image.com/giaithao.jpg", "Adidas", 50);
        Product product3 = new Product(3,"Quần jeans nữ", "Quần jeans nữ thời trang, phù hợp mùa hè", 200000, "https://link-to-image.com/quan-jeans.jpg", "Levi's", 200);
        Product product4 = new Product(4, "Túi xách", "Túi xách thời trang cho các nàng", 250000, "https://link-to-image.com/tui-xach.jpg", "Michael Kors", 150);

        addToCart(product1);
        addToCart(product2);
        addToCart(product3);
        addToCart(product4);
    }

    @PostMapping("/product/buyNow/{productId}")
    public String buyNow(@PathVariable Long productId) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            clearCart();
            addToCart(product);
            return "redirect:/checkout";
        }
        return "redirect:/home";
    }

    @PostMapping("/product/addToCart/{productId}")
    public String addToCartHandler(@PathVariable Long productId) {
        productRepo.findById(productId).ifPresent(this::addToCart);
        return "redirect:/cart";
    }

    @GetMapping("/product/{productId}")
    public String viewProduct(@PathVariable Long productId, Model model) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            model.addAttribute("product", product);
            return "redirect:/product-detail";
        }
        return "redirect:/home";
    }
}

