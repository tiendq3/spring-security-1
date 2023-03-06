package com.example.demo_security.controller;

import com.example.demo_security.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    @GetMapping("/api/products")
    public List<Product> getProduct() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("coffe 1", 150D));
        productList.add(new Product("coffe 2", 140D));
        productList.add(new Product("coffe 3", 130D));
        return productList;
    }

    @GetMapping("/api/hello")
    public String hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "hello " + authentication;
    }

    @GetMapping("/api/coffee")
    public String coffee() {
        return "coffee";
    }

    @GetMapping("/un-authorizes")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> unAuthorizes() {
        return ResponseEntity.ok("reason: un-authorizes; status-code: 403");
    }
}
