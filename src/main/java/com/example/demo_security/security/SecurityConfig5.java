package com.example.demo_security.security;

import com.example.demo_security.model.Authority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig5 extends WebSecurityConfigurerAdapter {

//    @Bean
//    public UserDetailsService inMemoryUserDetailsManager() {
//        Collection<UserDetails> users = new ArrayList<>();
//
//        Set<GrantedAuthority> authorTien1 = Stream.of(
//                Authority.CREATE,
//                Authority.EDIT,
//                Authority.DELETE,
//                Authority.READ,
//                Authority.SEARCH).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
//
//        var tien1 = User.builder()
//                .username("tien1")
//                .password(encoder().encode("123"))
//                .authorities(authorTien1)
//                .build();
//        var tien2 = User
//                .builder()
//                .username("tien2")
//                .password(encoder().encode("123"))
//                .authorities(Set.of(new SimpleGrantedAuthority(Authority.READ)))
//                .build();
//        var tien3 = User
//                .builder()
//                .username("tien3")
//                .password(encoder().encode("123"))
//                .authorities(Set.of(new SimpleGrantedAuthority(Authority.SEARCH)))
//                .build();
//
//        users.add(tien1);
//        users.add(tien2);
//        users.add(tien3);
//        return new InMemoryUserDetailsManager(users);
//    }

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
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/books").hasAnyAuthority(Authority.READ, Authority.CREATE, Authority.EDIT, Authority.DELETE)
                .antMatchers("/**/add", "/**/save").hasAnyAuthority(Authority.CREATE, Authority.EDIT, Authority.DELETE)
                .antMatchers("/**/delete/**").hasAuthority(Authority.DELETE)
                .antMatchers("/**/edit/**").hasAuthority(Authority.EDIT)
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                // x??c th???c th??nh c??ng chuy???n h?????ng ?????n api /welcome
                .defaultSuccessUrl("/welcome")
                // x??c th???c th???t b???i chuy???n h?????ng ?????n api /error-authentication
                .failureUrl("/error-authentication")
                // khi y??u c???u x??c th???c th?? chuy???n ?????n api /login
                .loginProcessingUrl("/login")
                .and()
                // kh??ng ????? quy???n redirect ?????n api /un-authorizes
                .exceptionHandling()
                .accessDeniedPage("/un-authorizes")
                .and()
                .logout();
//        http.exceptionHandling().accessDeniedPage("/").and().httpBasic();
    }
}
