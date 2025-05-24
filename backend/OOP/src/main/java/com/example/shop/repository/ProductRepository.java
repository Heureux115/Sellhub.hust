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
    List<Product> findByBrandIgnoreCaseAndCategoryIgnoreCase(String brand, String category);
    List<Product> findAllByOrderByPriceDesc();
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByTitleDesc();
    List<Product> findAllByOrderByTitleAsc();
    @Query("SELECT p FROM Product p WHERE TYPE(p) = :type") List<Product> findByType(@Param("type") Class<? extends Product> type);

    List<Product> findByCategoryIgnoreCaseAndBrandIgnoreCase(String category, String brand);
}

