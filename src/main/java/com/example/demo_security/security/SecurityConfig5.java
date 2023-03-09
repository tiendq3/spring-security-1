package com.example.demo_security.security;

import com.example.demo_security.model.Authority;
import com.example.demo_security.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig5 extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService inMemoryUserDetailsManager() {
        Collection<UserDetails> users = new ArrayList<>();

        Set<GrantedAuthority> authorTien1 = Stream.of(
                Authority.CREATE,
                Authority.EDIT,
                Authority.DELETE,
                Authority.READ,
                Authority.SEARCH).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        var tien1 = User.builder()
                .username("tien1")
                .password(encoder().encode("123"))
                .authorities(authorTien1)
                .build();
        var tien2 = User
                .builder()
                .username("tien2")
                .password(encoder().encode("123"))
                .authorities(Set.of(new SimpleGrantedAuthority(Authority.READ)))
                .build();
        var tien3 = User
                .builder()
                .username("tien3")
                .password(encoder().encode("123"))
                .authorities(Set.of(new SimpleGrantedAuthority(Authority.SEARCH)))
                .build();

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
        http
                .exceptionHandling()
                .accessDeniedPage("/un-authorizes")
                .and()
                .authorizeRequests()
                .antMatchers("/books").hasAnyAuthority(Authority.READ, Authority.CREATE, Authority.EDIT, Authority.DELETE)
                .antMatchers("/**/add", "/**/save").hasAnyAuthority(Authority.CREATE, Authority.EDIT, Authority.DELETE)
                .antMatchers("/**/delete/**").hasAuthority(Authority.DELETE)
                .antMatchers("/**/edit/**").hasAuthority(Authority.EDIT)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout();
    }
}
