package com.example.shop.controller;

import com.example.shop.model.*;
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
        return "brand-products";
    }

    //hàm tạo trang theo giá tiền
    @GetMapping("/price/{minPrice}/{maxPrice}")
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

    @GetMapping("/sort")
    public String sortProducts(@RequestParam String by, Model model) {
        List<Product> sortedProducts;

        switch (by.toLowerCase()) {
            case "price-desc":
                sortedProducts = productRepo.findAllByOrderByPriceDesc();
                break;
            case "price-asc":
                sortedProducts = productRepo.findAllByOrderByPriceAsc();
                break;
            case "title-desc":
                sortedProducts = productRepo.findAllByOrderByTitleDesc();
                break;
            case "title-asc":
                sortedProducts = productRepo.findAllByOrderByTitleAsc();
                break;
            default:
                sortedProducts = productRepo.findAll(); // mặc định không sắp xếp
                break;
        }

        model.addAttribute("products", sortedProducts);
        model.addAttribute("sortBy", by);
        return "home"; // hoặc một view cụ thể khác nếu bạn muốn
    }

    @GetMapping("/category/{name}")
    public String showCategory(@PathVariable("name") String name, Model model) {
        // Truy vấn danh sách hãng theo category (phone, laptop, ipad)
        List<String> brands = getBrandsByCategory(name);
        List<String> images = getItemsByBrand(name);
        List<String> names = getNameByBrand(name);
        model.addAttribute("images", images);
        model.addAttribute("names", names);
        model.addAttribute("category", name);
        model.addAttribute("brands", brands);

        return "category"; // trả về category.html để hiển thị danh sách hãng
    }

    private List<String> getNameByBrand(String category) {
        switch(category.toLowerCase()) {
            case "phone":
                return List.of("Iphone16ProMax", "HuaweiPura70", "SamsungGalaxyZFold6", "XiaomiRedmiNote11", "VertuSignatureCobra");
            case "laptop":
                return List.of("Apple", "Dell", "Lenovo", "Asus", "HP", "MSI", "Acer", "Razer");
            case "accessories":
                return List.of("Apple", "Samsung", "Sony", "Xiaomi", "Logitech");
            case "tablet":
                return List.of("Apple", "Samsung", "Xiaomi", "Sony", "Huawei");
            case "secondhand":
                return List.of();
            default:
                return List.of();
        }
    }

    private List<String> getItemsByBrand(String category) {
        switch(category.toLowerCase()) {
            case "phone":
                return List.of("Iphone16ProMax.jpg", "HuaweiPura70.jpg", "SamsungGalaxyZFold6.jpg", "XiaomiRedmiNote11.jpg", "VertuSignatureCobra.jpg");
            case "laptop":
                return List.of("Apple", "Dell", "Lenovo", "Asus", "HP", "MSI", "Acer", "Razer");
            case "accessories":
                return List.of("Apple", "Samsung", "Sony", "Xiaomi", "Logitech");
            case "tablet":
                return List.of("Apple", "Samsung", "Xiaomi", "Sony", "Huawei");
            case "secondhand":
                return List.of();
            default:
                return List.of();
        }
    }

    private List<String> getBrandsByCategory(String category) {
        switch(category.toLowerCase()) {
            case "phone":
                return List.of("Apple", "Samsung", "Xiaomi", "Nokia", "Huawei", "Vivo", "Oppo", "Vertu");
            case "laptop":
                return List.of("Apple", "Dell", "Lenovo", "Asus", "HP", "MSI", "Acer", "Razer");
            case "accessories":
                return List.of("Apple", "Samsung", "Sony", "Xiaomi", "Logitech");
            case "tablet":
                return List.of("Apple", "Samsung", "Xiaomi", "Sony", "Huawei");
            case "secondhand":
                return List.of();
            default:
                return List.of();
        }
    }


}
