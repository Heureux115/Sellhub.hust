    package com.example;

    import com.example.shop.model.Product;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.boot.autoconfigure.domain.EntityScan;
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

    @SpringBootApplication
    @ComponentScan(basePackages = {"com.example", "com.example.shop"})
    @EnableJpaRepositories(basePackages = {"com.example.shop.repository"})
    @EntityScan(basePackages = {"com.example.shop.model"})
    public class OopApplication {
        public static void main(String[] args) {
            SpringApplication.run(OopApplication.class, args);
        }
    }
