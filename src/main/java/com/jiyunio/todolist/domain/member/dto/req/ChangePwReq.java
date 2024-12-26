package com.jiyunio.todolist.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Schema(description = "회원 비밀번호 수정")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChangePwReq {
    @NotBlank(message = "비밀번호를 입력하세요.")
    @Schema(description = "회원 기존 비밀번호", example = "qwe123!")
    private final String userPw;

    @NotBlank(message = "변경 비밀번호를 입력하세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
    @Schema(description = "회원 변경 비밀번호", example = "qwer1234!")
    private final String changePw;

    @NotBlank(message = "확인 비밀번호를 입력하세요.")
    @Schema(description = "회원 변경 확인 비밀번호", example = "qwer1234!")
    private final String confirmChangePw;
}
