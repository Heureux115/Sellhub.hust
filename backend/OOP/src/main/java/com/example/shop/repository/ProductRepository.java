package com.example.shop.repository;

import com.example.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleContainingIgnoreCase(String keyword);
    List<Product> findAllByOrderByPriceDesc();
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByTitleDesc();
    List<Product> findAllByOrderByTitleAsc();
    List<Product> findByCategoryIgnoreCaseAndBrandIgnoreCase(String category, String brand);
    List<Product> findByCategoryAndPriceBetween(String category, double minPrice, double maxPrice);
    List<Product> findByCategoryAndBrandAndPriceBetween(String category, String Brand, double minPrice, double maxPrice);
}

