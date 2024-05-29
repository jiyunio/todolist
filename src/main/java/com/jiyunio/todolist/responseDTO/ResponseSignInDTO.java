package com.jiyunio.todolist.responseDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSignInDTO {
    @NotNull
    private Long memberId;

    @NotBlank
    private String userId;

    @NotBlank
    private String token;

    @Builder
    protected ResponseSignInDTO(Long memberId, String userId, String token) {
        this.memberId = memberId;
        this.userId = userId;
        this.token = token;
    }
}
