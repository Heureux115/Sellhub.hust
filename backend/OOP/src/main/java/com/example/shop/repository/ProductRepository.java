package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shop.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleContainingIgnoreCase(String title);
    List<Product> findByBrandIgnoreCase(String brand);
    List<Product> findByPrice(double price);
    List<Product> findByPriceBetween(double priceMin, double priceMax);

    @Query("SELECT p FROM Product p WHERE TYPE(p) = :type")
    List<Product> findByType(@Param("type") Class<? extends Product> type);
}
