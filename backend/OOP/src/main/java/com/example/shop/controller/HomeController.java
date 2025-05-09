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

    //tạo trang home
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("products", productRepo.findAll());
        return "home";
    }

    //hàm tìm sản phẩm
    @GetMapping("/search")
    public String searchProduct(@RequestParam(required = false) String keyword, Model model) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return "redirect:/home";
        }

        List<Product> matchingProducts = productRepo.findByTitleContainingIgnoreCase(keyword);
        if (matchingProducts.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy sản phẩm nào phù hợp");
            return "redirect:/search-result";
        }

        if (matchingProducts.size() == 1) {
            Long id = matchingProducts.get(0).getId();
            return "redirect:/product/" + id;
        }

        model.addAttribute("products", matchingProducts);
        return "redirect:/search-result";
    }

    //hàm tạo trang brand name
    @GetMapping("/brand/{name}")
    public String viewByBrand(@PathVariable String name, Model model) {
        List<Product> productsByBrand = productRepo.findByBrandIgnoreCase(name);
        model.addAttribute("products", productsByBrand);
        model.addAttribute("brand", name);
        return "redirect:/brand-products";
    }

    //hàm tạo trang theo giá tiền
    @GetMapping("/price/{price}")
    public String viewByPrice(@PathVariable double minPrice, @PathVariable double maxPrice,Model model) {
        List<Product> productsByPrice = productRepo.findByPriceBetween(minPrice, maxPrice);
        model.addAttribute("products", productsByPrice);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "redirect:/price-products";
    }

    //tạo trang hot product
    @GetMapping("/product/{id}")
    public String viewHotProduct(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id).orElse(null);

        if (product == null) {
            model.addAttribute("message", "Không tìm thấy sản phẩm.");
            return "redirect:/error";
        }

        model.addAttribute("product", product);
        return "redirect:/hot-product";
    }

}
