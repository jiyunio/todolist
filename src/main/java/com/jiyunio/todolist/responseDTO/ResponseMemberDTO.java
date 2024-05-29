package com.jiyunio.todolist.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMemberDTO {
    @NotNull
    @Schema(description = "회원의 Id", example = "1")
    private Long memberId;

    @NotBlank
    @Schema(description = "회원의 userId", example = "jiyun123")
    private String userId;

    @Builder
    protected ResponseMemberDTO(Long memberId, String userId) {
        this.memberId = memberId;
        this.userId = userId;
    }
}
