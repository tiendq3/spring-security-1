//package com.example.demo_security.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig2 extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public UserDetailsService inMemoryUserDetailsManager() {
//        Collection<UserDetails> users = new ArrayList<>();
////
//        User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder()::encode);
//        var tien1 = userBuilder.username("tien").password("123").authorities("read").build();
//        var tien2 = userBuilder.username("tien").password("123").authorities("read").build();
//        var tien3 = userBuilder.username("tien").password("123").authorities("read").build();
//
//        System.out.println(tien1.getPassword());
//
//        users.add(tien1);
//        users.add(tien2);
//        users.add(tien3);
//        return new InMemoryUserDetailsManager(users);
//    }
//
//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic();
//        http.authorizeRequests().anyRequest().authenticated();
//    }
//}
