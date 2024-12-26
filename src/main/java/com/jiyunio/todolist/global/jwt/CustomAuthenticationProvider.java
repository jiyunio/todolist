package com.jiyunio.todolist.global.jwt;

import com.jiyunio.todolist.global.customError.CustomException;
import com.jiyunio.todolist.global.customError.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    //인증을 처리하는 메소드 (로그인할 때 사용)
    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = authentication.getName();
        String userPw = (String) authentication.getCredentials();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        if (userDetails == null || !passwordEncoder.matches(userPw, userDetails.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.WRONG_USERID_PASSWORD);
        }
        //인증 완료
        return new UsernamePasswordAuthenticationToken(userId, "", userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationProvider.class.isAssignableFrom(authentication);
    }
}
