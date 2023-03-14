package com.example.demo_security.service;

import com.example.demo_security.model.Authority;
import com.example.demo_security.model.Login;
import com.example.demo_security.model.User;
import com.example.demo_security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void login(Login login) {

    }

    @Transactional
    public void register(Login login) {
        User user = userRepository.findByUsername(login.getUsername());
        if (user != null) throw new RuntimeException("username exist!");
        User newUser = User
                .builder()
                .username(login.getUsername())
                .password(passwordEncoder.encode(login.getPassword()))
                .authorities(Set.of(new SimpleGrantedAuthority(Authority.READ)))
                .build();
        userRepository.save(newUser);
    }
}
