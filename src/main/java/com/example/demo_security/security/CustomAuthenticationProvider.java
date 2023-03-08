package com.example.demo_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * lớp nhận xử lý thông tin người dùng nhập vào để xác thực
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * xác thực đối tượng authentication (thông tin của người dùng nhập vào),
     * so sánh với thông tin user ở trong userDetailsService.
     * 1. xác thực thành công trả về 1 đối tượng Authentication có trường authenticated = true
     * ( đồng nghĩa đã xác thực và phân quyền)
     * 2 . xác thực thất bại ném ra exception
     * @param authentication the authentication request object.
     * @return
     * @throws AuthenticationException
     */

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        boolean matches = passwordEncoder.matches((CharSequence) authentication.getCredentials(), userDetails.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(),
                authentication.getCredentials(),
                authentication.getAuthorities());
        if (matches)
            return authenticationToken;
        return null;
    }

    /**
     * cho phép những đối tượng loại lớp nào được xác thực.
     * trả về true - hỗ trợ đối tượng lớp đó dc xác thực ở phương thức phía trên.
     *        false - không hỗ trợ
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
