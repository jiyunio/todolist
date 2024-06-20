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
import com.jiyunio.todolist.todo.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TodoService todoService;
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
            categoryService.createCategory(member.getUserId(), CategoryDTO.builder()
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
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInDto.getUserId(), signInDto.getUserPw());
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);
        return jwtProvider.createToken(authentication);
    }

    public ResponseMemberDTO getMember(String userId) {
        Member member = memberRepository.findByUserId(userId).get();
        return ResponseMemberDTO.builder()
                .memberId(member.getId())
                .userId(member.getUserId())
                .build();
    }

    public ResponseMemberDTO updateUserPw(String userId, @Valid ChangeUserPwDTO changeUserPwDto) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );
        if (passwordEncoder.matches(changeUserPwDto.getUserPw(), member.getUserPw())) { // 회원 비밀번호 확인
            if (changeUserPwDto.getChangePw().equals(changeUserPwDto.getConfirmChangePw())) {
                // 비밀번호 업데이트 성공
                member.updateUserPw(passwordEncoder.encode(changeUserPwDto.getChangePw()));
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

    public void deleteMember(String userId) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );
        todoService.deleteTodos(userId);
        categoryService.deleteCategories(userId);
        memberRepository.deleteById(member.getId());
    }
}
