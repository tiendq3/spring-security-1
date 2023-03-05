package com.example.demo_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig1 extends WebSecurityConfigurerAdapter {

    // Tạo ra Bean InMemoryUserDetailsManager ( UserDetailsService) quản lý user1, user2 để xác thực
    @Bean
    public UserDetailsService userDetailsService() {
        var user1 = User.withUsername("tien1").password("123").authorities("read").build();
        var user2 = User.withUsername("tien2").password("123").authorities("write").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    // Tạo ra Bean NoOpPasswordEncoder.getInstance() để mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Cấu hình trực tiếp AuthenticationManagerBuilder quản lý userDetailsService() để xác thực
     * và nó dùng passwordEncoder() để mã hóa mật khẩu.
     * Tuy nhiên có thể không cần method này vì ta đã tạo ra 2 Bean UserDetailsService
     * và PasswordEncoder thì spring sẽ mặc định tiêm vào các Bean khác để dùng.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /** http.formLogin(); dùng formLogin để đăng nhập, nhưng dùng phương thức POST để truyền dữ liệu
         * http.httpBasic(); dùng popup để đăng nhập, nhưng dùng HTTP Basic Authentication và truyền dữ liệu
         * qua Header của request: trường Authorization
         */
        http.formLogin();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
