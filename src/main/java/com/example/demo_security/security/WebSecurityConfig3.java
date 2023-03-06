package com.example.demo_security.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig3 extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService inMemoryUserDetailsManager() {
        Collection<UserDetails> users = new ArrayList<>();

        User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder()::encode);
        var tien1 = userBuilder.username("tien1").password("123").roles("Admin").build();
        var tien2 = userBuilder.username("tien2").password("123").roles("Operator").build();
        var tien3 = userBuilder.username("tien3").password("123").roles("User").build();

// -> password của UserDetails ở dạng encode ( userdetails - đối tượng chính để mapping authen)
        log.warn("PASSWORD encode: " + tien1.getPassword());

        users.add(tien1);
        users.add(tien2);
        users.add(tien3);
        return new InMemoryUserDetailsManager(users);
    }

    // Tạo 1 Bean PasswordEncoder lấy trong HashMap encoders;

    @Bean
    public PasswordEncoder encoder() {
        return delegatingPasswordEncoder("bcrypt");
    }

    /** method trả về DelegatingPasswordEncoder là con của PasswordEncoder nhưng mục đích
     * để quản lý các PasswordEncoder khác bằng HashMap
     * @param encodingType
     * @return
     */
    public static PasswordEncoder delegatingPasswordEncoder(String encodingType) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(encodingType, encoders);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // bắt exception khi xác thực hoặc phân quyền xảy ra lỗi
                .exceptionHandling()
                // điều hướng đến api /un-authorizes
                .accessDeniedPage("/un-authorizes")
                .and()
                // xử lý phân quyền các api
                .authorizeRequests()
                // phải viết đúng url của api (hoăc là có thể dùng ** nhưng phải kèm theo /)
                // patten api và hasAnyRole ít nhất 1 trong 2 role đã định
                .antMatchers("/**/products").hasAnyRole("Admin", "User")
                .antMatchers("/**/hello").hasAnyRole("Admin", "Operator")
                .antMatchers("/**/coffee").hasAnyRole("Admin", "Operator")
                // các request còn lại cần phải xác thực
                .anyRequest().authenticated()
                .and()
                // dùng form login để đăng nhập
                .formLogin()
                // khi login để chuyển trên api /login
                .loginProcessingUrl("/login")
                // tất cả người dùng đều có thể truy cập api này
                .permitAll();
    }
}
