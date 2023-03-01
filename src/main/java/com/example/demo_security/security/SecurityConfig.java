package com.example.demo_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration - tạo bean với mục đích dùng để config trong spring
//@EnableWebSecurity - gắn vào class mục đích để custom security
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // tạo ra bean bằng method ( kết hợp với @Configuration)
    @Bean
    // UserDetailsService quản lý các UserDetails để xác thực
    public UserDetailsService userDetailsService() {

        // tạo mới UserDetails user1 bằng cách sử dụng UserBuilder
        var user1 = User.withUsername("tien").password("123").authorities("read").build();

        // trả về InMemoryUserDetailsManager quản lý user1 trong bộ nhớ
        return new InMemoryUserDetailsManager(user1);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        // tạo ra bean NoOpPasswordEncoder.getInstance() là 1 thể hiện của PasswordEncoder
        // rawPassword.toString().equals(encodedPassword) -> chuỗi mật khẩu không thay đổi sau khi encode
        return NoOpPasswordEncoder.getInstance();
    }
}
