package com.jiyunio.todolist.jwt;

import com.jiyunio.todolist.customError.CustomException;
import com.jiyunio.todolist.customError.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    @Value("${spring.jwt.secret}")
    private String secretKey;
    private final CustomUserDetailsService customUserDetailsService;
    private final long tokenValidTime = 60 * 60 * 1000L;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public JwtDTO createToken(Authentication authentication) {
        Date now = new Date();
        String authority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().toString();

        String accessToken = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .claim("auth", authority)
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(getSecretKey())
                .compact();

        return new JwtDTO(accessToken);
    }

    //SecurityContext에 인증된 클라이언트의 Authentication를 저장하기 위해 사용됨
    public Authentication getAuthentication(String accessToken) {
        //인증 객체 반환 (로그인 이외의 토큰 인증할 때 "Jwt 필터" 에서 이용)
        Claims claims = parseClaims(accessToken);
        if (claims.get("auth") == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorCode.NO_AUTHORIZATION);
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }

    public String resolveToken(HttpServletRequest request) {
        //request의 header에서 token 가져오기
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            return token.substring(7);
        }
        return null;
    }

    public boolean validationToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(accessToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorCode.NO_AUTHORIZATION);
        }

    }

    public Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }
}
