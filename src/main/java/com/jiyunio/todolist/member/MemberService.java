package com.jiyunio.todolist.member;

import com.jiyunio.todolist.category.CategoryDTO;
import com.jiyunio.todolist.category.CategoryService;
import com.jiyunio.todolist.customError.CustomException;
import com.jiyunio.todolist.customError.ErrorCode;
import com.jiyunio.todolist.jwt.CustomAuthenticationProvider;
import com.jiyunio.todolist.jwt.JwtDTO;
import com.jiyunio.todolist.jwt.JwtProvider;
import com.jiyunio.todolist.member.dto.ChangeUserPwDTO;
import com.jiyunio.todolist.member.dto.SignInDTO;
import com.jiyunio.todolist.member.dto.SignUpDTO;
import com.jiyunio.todolist.responseDTO.ResponseMemberDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CategoryService categoryService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomAuthenticationProvider authenticationProvider;
    private final JwtProvider jwtProvider;

    public ResponseMemberDTO signUp(@Valid SignUpDTO signUpDto) {
        if (memberRepository.existsByUserId(signUpDto.getUserId())) {
            // 이미 존재하는 아이디
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.EXIST_USERID);
        }

        if (signUpDto.getUserPw().equals(signUpDto.getConfirmUserPw())) {
            // 회원가입 성공

            Member member = Member.builder()
                    .userId(signUpDto.getUserId())
                    .userPw(passwordEncoder.encode(signUpDto.getUserPw()))
                    .build();

            memberRepository.save(member);

            //기본 카테고리 동시에 생성
            categoryService.createCategory(member.getId(), CategoryDTO.builder()
                    .content("기본")
                    .color("FFFFFF").build());

            return ResponseMemberDTO.builder()
                    .memberId(member.getId())
                    .userId(member.getUserId())
                    .build();
        }
        // 비밀번호 불일치
        throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_SAME_CONFIRM_PASSWORD);
    }

    public JwtDTO signIn(@Valid SignInDTO signInDto) {
//        if (memberRepository.existsByUserId(signInDto.getUserId())) {
//            Member member = memberRepository.findByUserId(signInDto.getUserId()).get();
//            if (passwordEncoder.matches(signInDto.getUserPw(), member.getUserPw())) {
//                // 로그인 성공
//                return ResponseMemberDTO.builder()
//                        .memberId(member.getId())
//                        .userId(member.getUserId())
//                        .build();
//            }
//        }
//        // 아이디 및 회원 비밀번호 불일치
//        throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.WRONG_USERID_PASSWORD);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInDto.getUserId(), signInDto.getUserPw());
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);
        JwtDTO jwtDTO = jwtProvider.createToken(authentication);
        return jwtDTO;
    }

    public List<ResponseMemberDTO> getMembers() {
        List<Member> members = memberRepository.findAll();
        List<ResponseMemberDTO> getMembers = new ArrayList<>();
        for (Member member: members) {
            getMembers.add(ResponseMemberDTO.builder()
                    .memberId(member.getId())
                    .userId(member.getUserId())
                    .build());
        }
        return getMembers;
    }

    public ResponseMemberDTO updateUserPw(Long id, @Valid ChangeUserPwDTO changeUserPwDto) {
        Member member = memberRepository.findById(id).get();
        if (member.getUserPw().equals(changeUserPwDto.getUserPw())) { // 회원 비밀번호 확인
            if (changeUserPwDto.getChangePw().equals(changeUserPwDto.getConfirmChangePw())) {
                // 비밀번호 업데이트 성공
                member.updateUserPw(changeUserPwDto.getChangePw());
                memberRepository.save(member);

                return ResponseMemberDTO.builder()
                        .memberId(member.getId())
                        .userId(member.getUserId())
                        .build();
            } else {
                // 변경 비밀번호 불일치
                throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_SAME_CONFIRM_PASSWORD);
            }
        } else {
            // 회원의 비밀번호와 불일치
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.WRONG_USERID_PASSWORD);
        }
    }

    public void deleteMember(Long id, String userPw) {
        Member member = memberRepository.findById(id).get();
        if (member.getUserPw().equals(userPw)) {
            // 회원 탈퇴 성공
            memberRepository.deleteById(id);
        } else {
            // 비밀번호 불일치
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.WRONG_USERID_PASSWORD);
        }
    }
}
