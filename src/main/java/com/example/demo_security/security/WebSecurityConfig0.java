package com.example.demo_security.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig0 extends WebSecurityConfigurerAdapter {
    /**
     * cấu hình trực tiếp AuthenticationManagerBuilder quản lý InMemoryAuthentication (UserDetailsService)
     * InMemoryAuthentication quản lý User(username,password,authorities) dùng để xác thực
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .inMemoryAuthentication()
                .withUser("tien")
                .password("123")
                .authorities("read")
                .and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        cấu hình http dùng popup login để đăng nhập (cách thức xác thức Basic Authentication)
        http.httpBasic();
//        xác thực và phân quyền request: tất cả request đều phải xác thực
        http.authorizeRequests().anyRequest().authenticated();
    }
}

