package com.example.shop.controller;

import com.example.shop.model.*;
import jakarta.servlet.http.HttpSession;
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
    public String home(HttpSession session, Model model) {
        // Thêm sản phẩm vào model như cũ
        model.addAttribute("products", productRepo.findAll());

        // Lấy user từ session
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        return "home";
    }


    //hàm tìm sản phẩm
    @GetMapping("/search")
    public String searchProduct(@RequestParam(required = false) String keyword, Model model, HttpSession session) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return "redirect:/home";
        }
        User user = (User) session.getAttribute("user");
        List<Product> matchingProducts = productRepo.findByTitleContainingIgnoreCase(keyword);
        if (matchingProducts.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy sản phẩm nào phù hợp");
            return "search-result";
        }

        if (matchingProducts.size() == 1) {
            Long id = matchingProducts.get(0).getId();
            return "redirect:/product/" + id;
        }
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        model.addAttribute("products", matchingProducts);
        return "search-result";
    }

    //hàm tạo trang brand name
    @GetMapping("/category/{category}/brand/{brand}")
    public String viewByBrandAndCategory(@PathVariable String category, @PathVariable String brand, Model model, HttpSession session) {
        // Lấy sản phẩm theo category và brand
        List<Product> products = productRepo.findByCategoryIgnoreCaseAndBrandIgnoreCase(category, brand);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        model.addAttribute("products", products);
        model.addAttribute("brands", brand);
        model.addAttribute("category", category);
        return "brand-products";
    }


    //hàm tạo trang theo giá tiền
    @GetMapping("/price/{minPrice}/{maxPrice}")
    public String viewByPrice(@PathVariable int minPrice, @PathVariable int maxPrice, Model model, HttpSession session) {
        List<Product> productsByPrice = productRepo.findByPriceBetween(minPrice, maxPrice);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        model.addAttribute("products", productsByPrice);
        return "price-products";
    }

    @GetMapping("/category/{category}/price/{minPrice}/{maxPrice}")
    public String viewByPrice(
            @PathVariable String category,
            @PathVariable int minPrice,
            @PathVariable int maxPrice,
            Model model,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Product> products = productRepo.findByCategoryAndPriceBetween(
                category, minPrice, maxPrice);
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        return "price-products";
    }
    @GetMapping("/category/{category}/brand/{brand}/price/{minPrice}/{maxPrice}")
    public String viewByPrice(
            @PathVariable String category,
            @PathVariable String brand,
            @PathVariable int minPrice,
            @PathVariable int maxPrice,
            Model model,
            HttpSession session) {
    User user = (User) session.getAttribute("user");
        List<Product> products = productRepo.findByCategoryAndBrandAndPriceBetween(
                category, brand, minPrice, maxPrice);
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("brand", brand);
        return "price-products";
    }

    @GetMapping("/category/{name}")
    public String showCategory(@PathVariable("name") String name, Model model, HttpSession session) {
        // Truy vấn danh sách hãng theo category (phone, laptop, ipad)
        List<String> brands = getBrandsByCategory(name);
        List<String> images = getItemsByBrand(name);
        List<String> names = getNameByBrand(name);
        String pageTitle = getTitleByCategory(name);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }

        model.addAttribute("images", images);
        model.addAttribute("names", names);
        model.addAttribute("category", name);
        model.addAttribute("brands", brands);
        model.addAttribute("pageTitle", pageTitle);

        return "category"; // trả về category.html để hiển thị danh sách hãng
    }

    private String getTitleByCategory(String category) {
        switch (category.toLowerCase()) {
            case "phone":
                return "Điện thoại";
            case "laptop":
                return "Laptop";
            case "accessories":
                return "Phụ kiện";
            case "tablet":
                return "Máy tính bảng";
            default:
                return "Danh mục sản phẩm";
        }
    }

    private List<String> getNameByBrand(String category) {
        switch(category.toLowerCase()) {
            case "phone":
                return List.of("Iphone 16 Pro Max", "Huawei Pura 70", "Samsung Galaxy ZFold 6", "Xiaomi Redmi Note 11", "Vertu Signature Cobra");
            case "laptop":
                return List.of("Macbook Air M1 2020", "Macbook Air M2 2022", "Lenovo ThinkPad X1 Carbon Gen 10", "Asus Vivobook 15");
            case "accessories":
                return List.of("Airpod 4", "Havit H663BT", "Marshall Major V", "Sony WH-CH520", "Apple MagSafe 25W","Xiaomi Router AC 12004A");
            case "tablet":
                return List.of("iPad A16 WiFi", "iPad Air M3 11 inch WiFi", "Oppo Pad 3", "Samsung Galaxy Tab S10 5G", "Xiaomi Pad 7 WiFi");
            default:
                return List.of();
        }
    }

    private List<String> getItemsByBrand(String category) {
        switch(category.toLowerCase()) {
            case "phone":
                return List.of("Iphone16ProMax.jpg", "HuaweiPura70.jpg", "SamsungGalaxyZFold6.jpg", "XiaomiRedmiNote11.jpg", "VertuSignatureCobra.jpg");
            case "laptop":
                return List.of("MacbookAirM12020.jpg", "MacbookAirM22022.jpg", "LenovoThinkPadX1CarbonGen10.jpg", "AsusVivobook15.jpg");
            case "accessories":
                return List.of("Airpod4.jpg", "HavitH663BT.jpg", "MarshallMajorV.jpg", "SonyWH-CH520.jpg", "AppleMagSafe25W.jpg","XiaomiRouterAC12004A.jpg" );
            case "tablet":
                return List.of("iPadA16WiFi.jpg", "iPadAirM311inchWiFi.jpg", "OppoPad3.jpg", "SamsungGalaxyTabS105G.jpg", "XiaomiPad7WiFi.jpg");
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
                return List.of("Apple", "Samsung", "Sony", "Xiaomi", "Logitech", "Marshall", "Oppo", "Huawei");
            case "tablet":
                return List.of("Apple", "Samsung", "Xiaomi", "Oppo", "Huawei");
            default:
                return List.of();
        }
    }
}
