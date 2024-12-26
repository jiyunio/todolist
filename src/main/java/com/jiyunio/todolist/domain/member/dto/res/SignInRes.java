package com.jiyunio.todolist.domain.member.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignInRes {
    @NotBlank
    @Schema(description = "회원의 userId", example = "qwe123")
    private String userId;

    @NotBlank
    @Schema(description = "회원의 token")
    private String token;
}
