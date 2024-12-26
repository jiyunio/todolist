package com.jiyunio.todolist.global.jwt;

import com.jiyunio.todolist.global.customError.CustomException;
import com.jiyunio.todolist.global.customError.ErrorCode;
import com.jiyunio.todolist.domain.member.Member;
import com.jiyunio.todolist.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws CustomException {
        Member member = memberRepository.findByUserId(username).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER));
        return new CustomUserDetails(member);
    }
}
