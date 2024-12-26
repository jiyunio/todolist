package com.jiyunio.todolist.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원가입")
public class SignUpReq {

    @NotBlank(message = "아이디를 입력하세요.")
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=\\S+$).{5,10}", message = "아이디 : 5~10자")
    @Schema(description = "회원의 userId \n\n- 영문 대/소문자 5~10자", example = "qwe123")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호: 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용하십쇼.")
    @Schema(description = "회원의 비밀번호 \n\n- 8~16자의 영문 대/소문자, 숫자, 특수문자", example = "qwer123!")
    private String userPw;

    @NotBlank(message = "확인 비밀번호를 입력하세요.")
    @Schema(description = "회원 확인 비밀번호", example = "qwer123!")
    private String confirmUserPw;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Schema(description = "닉네임", example = "곽두철")
    private String nickname;
}
