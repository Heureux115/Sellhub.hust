package com.example.shop.controller;

import com.example.shop.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.shop.repository.ProductRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private final ProductRepository productRepo;

    public HomeController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("products", productRepo.findAll());
        return "home";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("products", productRepo.findAll());
        return "cart";
    }

    @GetMapping("/login")
    public String logout(Model model) {
        return "login";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam(required = false) String keyword, Model model) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return "redirect:/";
        }

        List<Product> matchingProducts = productRepo.findByTitleContainingIgnoreCase(keyword);
        if (matchingProducts.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy sản phẩm nào phù hợp");
            return "search-result";
        }

        if (matchingProducts.size() == 1) {
            Long id = matchingProducts.get(0).getId();
            return "redirect:/product/" + id;
        }

        model.addAttribute("products", matchingProducts);
        return "search-result";
    }

    @GetMapping("/brand/{brandName}")
    public String viewByBrand(@PathVariable String brandName, Model model) {
        List<Product> productsByBrand = productRepo.findByBrandIgnoreCase(brandName);
        model.addAttribute("products", productsByBrand);
        model.addAttribute("brand", brandName);
        return "brand-products";
    }

    @GetMapping("/price/{price}")
    public String viewByPrice(@PathVariable double price, Model model) {
        List<Product> productsByPrice = productRepo.findByPrice(price);
        model.addAttribute("products", productsByPrice);
        model.addAttribute("price", price);
        return "price-products";
    }

    @GetMapping("/product/{id}")
    public String viewHotProduct(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id).orElse(null);

        if (product == null) {
            model.addAttribute("message", "Không tìm thấy sản phẩm.");
            return "error";
        }

        model.addAttribute("product", product);
        return "hot-product";
    }



}
