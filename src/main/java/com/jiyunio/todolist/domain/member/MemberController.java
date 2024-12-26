package com.jiyunio.todolist.domain.member;

import com.jiyunio.todolist.domain.member.dto.req.ChangePwReq;
import com.jiyunio.todolist.domain.member.dto.req.SignInReq;
import com.jiyunio.todolist.domain.member.dto.req.SignUpReq;
import com.jiyunio.todolist.domain.member.dto.res.MemberRes;
import com.jiyunio.todolist.domain.member.dto.res.SignInRes;
import com.jiyunio.todolist.global.customError.ErrorDTO;
import com.jiyunio.todolist.global.jwt.CustomUserDetails;
import com.jiyunio.todolist.global.jwt.JwtDTO;
import com.jiyunio.todolist.global.responseDTO.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = MemberRes.class)))
    @ApiResponse(responseCode = "400", description = "빈칸 / 이미 존재하는 아이디 / 비밀번호 조건 미달 / 확인 비밀번호 불일치", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<MemberRes> signUp(@Valid @RequestBody SignUpReq signUpReq) {
        return new ResponseEntity<>(memberService.signUp(signUpReq), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "아이디와 비밀번호 이용")
    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = MemberRes.class)))
    @ApiResponse(responseCode = "400", description = "빈칸", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "401", description = "회원 및 비밀번호 불일치", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<SignInRes> signIn(@Valid @RequestBody SignInReq signInReq) {
        return ResponseEntity.ok(memberService.signIn(signInReq));
    }

    @GetMapping("")
    @Operation(summary = "회원 조회")
    @ApiResponse(responseCode = "200", description = "회원 조회 성공", content = @Content(schema = @Schema(implementation = MemberRes.class)))
    public ResponseEntity<MemberRes> getMember(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(memberService.getMember(user.getUsername()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberRes>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

    @PutMapping("")
    @Operation(summary = "회원 비밀번호 수정", description = "비밀번호, 수정 비밀번호 이용")
    @ApiResponse(responseCode = "200", description = "비밀번호 업데이터 성공", content = @Content(schema = @Schema(implementation = MemberRes.class)))
    @ApiResponse(responseCode = "400", description = "빈칸", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "401", description = "회원 및 비밀번호 불일치 / 변경 비밀번호 불일치", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<MemberRes> updateUserPw(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody ChangePwReq changePwReq) {
        return ResponseEntity.ok(memberService.updateUserPw(user.getUsername(), changePwReq));
    }

    @PatchMapping("/{nickname}")
    @Operation(summary = "닉네임 수정")
    @ApiResponse(responseCode = "200", description = "닉네임 수정 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "존재하지 않는 회원", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<MemberRes> patchNickname(@AuthenticationPrincipal CustomUserDetails user, @PathVariable String nickname) {
        return ResponseEntity.ok(memberService.updateNickname(user.getUsername(), nickname));
    }

    @DeleteMapping("")
    @Operation(summary = "회원 탈퇴")
    @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "존재하지 않는 회원", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<ResponseDTO> deleteMember(@AuthenticationPrincipal CustomUserDetails user) {
        memberService.deleteMember(user.getUsername());
        return ResponseEntity.ok(ResponseDTO.builder()
                .msg("회원 탈퇴 성공")
                .build());
    }
}

