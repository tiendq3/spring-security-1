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

    @Bean
    public PasswordEncoder encoder() {
        return delegatingPasswordEncoder("bcrypt");
    }

    public static PasswordEncoder delegatingPasswordEncoder(String encodingType) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(encodingType, encoders);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
//        http.formLogin();
        http
//                .exceptionHandling()
//                .accessDeniedPage("/un-authorizes")
//                .and()
                .authorizeRequests()
                .antMatchers("api/products").hasRole("Admin")
                .antMatchers("api/hello").hasRole("Admin")
                .antMatchers("api/coffee").hasRole("Admin")
                .antMatchers("/**").authenticated();
    }
}
