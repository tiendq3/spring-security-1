package com.example.demo_security.security;

import com.example.demo_security.model.User;
import com.example.demo_security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("not found user by username: " + username);
        return User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities()).build();
    }
}
