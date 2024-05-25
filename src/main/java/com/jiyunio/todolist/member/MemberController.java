package com.jiyunio.todolist.member;

import com.jiyunio.todolist.member.dto.ChangeUserPwDTO;
import com.jiyunio.todolist.member.dto.SignInDTO;
import com.jiyunio.todolist.member.dto.SignUpDTO;
import com.jiyunio.todolist.responseDTO.ResponseDTO;
import com.jiyunio.todolist.responseDTO.ResponseMemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "Member", description = "회원 API")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입",
            description = "아이디, 비밀번호 이용\n\n 아이디 : 5 ~ 10자\n\n 비밀번호: 8~16자의 영문 대/소문자, 숫자, 특수문자")
    public ResponseEntity<ResponseMemberDTO> signUp(@Valid @RequestBody SignUpDTO signUpDto) {
        return new ResponseEntity<>(memberService.signUp(signUpDto), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "아이디와 비밀번호 이용" +
            "\n\n유효성 검사\n\n- 아이디 & 비밀번호 모두 존재 및 일치해야 함")
    public ResponseEntity<ResponseMemberDTO> signIn(@Valid @RequestBody SignInDTO signInDto) {
        return ResponseEntity.ok(memberService.signIn(signInDto));
    }

    @GetMapping("/members")
    @Operation(summary = "회원 전체 조회")
    public List<ResponseMemberDTO> getMember(){
        return memberService.getMembers();
    }

    @PutMapping("/{memberId}")
    @Operation(summary = "회원 비밀번호 수정", description = "비밀번호, 수정 비밀번호 이용")
    public ResponseEntity<ResponseMemberDTO> updateUserPw(@Parameter(description = "member의 id") @PathVariable Long memberId, @Valid @RequestBody ChangeUserPwDTO changeUserPwDto) {
        return ResponseEntity.ok(memberService.updateUserPw(memberId, changeUserPwDto));
    }

    @DeleteMapping("/{memberId}")
    @Operation(summary = "회원 탈퇴", description = "비밀번호 이용")
    public ResponseEntity<ResponseDTO> deleteMember(@Parameter(description = "member의 id") @PathVariable Long memberId, @RequestParam String userPw) {
        memberService.deleteMember(memberId, userPw);
        return ResponseEntity.ok(ResponseDTO.builder()
                .msg("회원 탈퇴 성공")
                .build());
    }
}

