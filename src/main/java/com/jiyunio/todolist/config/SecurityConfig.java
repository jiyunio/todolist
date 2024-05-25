//package com.jiyunio.todolist.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//@EnableMethodSecurity
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//    @Bean
//    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                //csrf 없애기
//                .csrf(csrf -> csrf.disable())
//
//                //접근 제어
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/member/sign-up").permitAll()
//                        .requestMatchers("/member/sign-in").permitAll()
//                        .anyRequest().authenticated()) //외의 접근은 인증 필수!
//
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }
//}
