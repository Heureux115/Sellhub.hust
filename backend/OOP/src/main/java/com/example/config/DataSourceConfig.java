package com.example.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:sqlserver://localhost:1433;databaseName=shopdb;encrypt=true;trustServerCertificate=true")
                .username("sa")
                .password("123456")
                .build();
    }
}
