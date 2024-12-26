package com.jiyunio.todolist.domain.member.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRes {
    @NotNull
    @Schema(description = "회원의 id", example = "1")
    private Long memberId;

    @NotBlank
    @Schema(description = "회원의 userId", example = "qwe123")
    private String userId;

    @NotBlank
    private String token;

    @Builder
    protected SignInRes(Long memberId, String userId, String token) {
        this.memberId = memberId;
        this.userId = userId;
        this.token = token;
    }
}
