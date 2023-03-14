package com.example.demo_security.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public String csvFile() {
        return "product.csv";
    }

    @Bean
    @Qualifier("csvFile1")
    public String csvFile(String name) {
        return name;
    }
}
