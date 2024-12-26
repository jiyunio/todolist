package com.jiyunio.todolist.domain.member;

import com.jiyunio.todolist.domain.category.CategoryService;
import com.jiyunio.todolist.domain.category.dto.CategoryReq;
import com.jiyunio.todolist.domain.member.dto.req.ChangePwReq;
import com.jiyunio.todolist.domain.member.dto.req.SignInReq;
import com.jiyunio.todolist.domain.member.dto.req.SignUpReq;
import com.jiyunio.todolist.domain.member.dto.res.MemberRes;
import com.jiyunio.todolist.domain.todo.TodoService;
import com.jiyunio.todolist.global.customError.CustomException;
import com.jiyunio.todolist.global.customError.ErrorCode;
import com.jiyunio.todolist.global.jwt.CustomAuthenticationProvider;
import com.jiyunio.todolist.global.jwt.JwtDTO;
import com.jiyunio.todolist.global.jwt.JwtProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final TodoService todoService;
    private final CategoryService categoryService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomAuthenticationProvider authenticationProvider;
    private final JwtProvider jwtProvider;

    public MemberRes signUp(@Valid SignUpReq signUpReq) {
        if (memberRepository.existsByUserId(signUpReq.getUserId())) {
            // 이미 존재하는 아이디
            throw new CustomException(HttpStatus.CONFLICT, ErrorCode.EXIST_USERID);
        }

        if (signUpReq.getUserPw().equals(signUpReq.getConfirmUserPw())) {
            // 회원가입 성공
            Member member = Member.builder()
                    .userId(signUpReq.getUserId())
                    .userPw(passwordEncoder.encode(signUpReq.getUserPw()))
                    .nickname(signUpReq.getNickname())
                    .build();

            memberRepository.save(member);

            //기본 카테고리 동시에 생성
            categoryService.createCategory(member.getUserId(), CategoryReq.builder()
                    .content("기본")
                    .color("FFFFFF").build());

            return MemberRes.builder()
                    .memberId(member.getId())
                    .userId(member.getUserId())
                    .nickname(member.getNickname())
                    .build();
        }
        // 비밀번호 불일치
        throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_SAME_CONFIRM_PASSWORD);
    }

    public JwtDTO signIn(@Valid SignInReq signInReq) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInReq.getUserId(), signInReq.getUserPw());
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);
        return jwtProvider.createToken(authentication);
    }

    public MemberRes getMember(String userId) {
        Member member = memberRepository.findByUserId(userId).get();
        return MemberRes.builder()
                .memberId(member.getId())
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .build();
    }

    public List<MemberRes> getMembers() {
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream().map(MemberRes::from).toList();
    }


    public MemberRes updateUserPw(String userId, @Valid ChangePwReq changePwReq) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );

        if (passwordEncoder.matches(changePwReq.getUserPw(), member.getUserPw())) { // 회원 비밀번호 확인
            if (changePwReq.getChangePw().equals(changePwReq.getConfirmChangePw())) {
                // 비밀번호 업데이트 성공
                member.updateUserPw(passwordEncoder.encode(changePwReq.getChangePw()));

                return MemberRes.builder()
                        .memberId(member.getId())
                        .userId(member.getUserId())
                        .nickname(member.getNickname())
                        .build();
            } else {
                // 변경 비밀번호 불일치
                throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_SAME_CONFIRM_PASSWORD);
            }
        } else {
            // 회원의 비밀번호와 불일치
            throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorCode.WRONG_USERID_PASSWORD);
        }
    }

    public MemberRes updateNickname(String userId, String nickname) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );
        member.updateNickname(nickname);

        return MemberRes.builder()
                .memberId(member.getId())
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .build();
    }

    public void deleteMember(String userId) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );
        todoService.deleteAllTodo(userId);
        categoryService.deleteCategories(userId);
        memberRepository.deleteById(member.getId());
    }
}
