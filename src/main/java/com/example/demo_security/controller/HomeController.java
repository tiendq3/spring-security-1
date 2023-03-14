package com.example.demo_security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/welcome")
    public ResponseEntity<Authentication> welcome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(authentication);
    }


    // xác thực thất bại ( sai username, password..)
    @GetMapping("/error-authentication")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String errorAuthentication() {
        return "reason: error authentication; status-code: 401";
    }


    // không đủ quyền
    @GetMapping("/un-authorizes")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> unAuthorizes() {
        return ResponseEntity.ok("reason: un-authorizes; status-code: 403");
    }
}
