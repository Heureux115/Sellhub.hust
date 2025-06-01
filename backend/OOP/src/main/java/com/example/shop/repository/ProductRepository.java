package com.example.shop.repository;

import com.example.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleContainingIgnoreCase(String keyword);
    List<Product> findByBrandIgnoreCase(String brand);
    List<Product> findByPriceBetween(double min, double max);
    List<Product> findByCategoryIgnoreCaseAndBrandIgnoreCase(String category, String brand);
    List<Product> findByCategoryAndPriceBetween(String category, double minPrice, double maxPrice);
    List<Product> findByCategoryAndBrandAndPriceBetween(String category, String Brand, double minPrice, double maxPrice);
}


