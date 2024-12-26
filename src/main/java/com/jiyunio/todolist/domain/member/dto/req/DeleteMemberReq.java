package com.jiyunio.todolist.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteMemberReq {
    @NotBlank(message = "아이디를 입력하세요.")
    @Schema(description = "회원의 userId", example = "qwe123")
    private final String userId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Schema(description = "회원의 비밀번호", example = "qwer123!")
    private final String userPw;
}
