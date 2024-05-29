package com.jiyunio.todolist.responseDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMemberDTO {
    @NotNull
    private Long memberId;

    @NotBlank
    private String userId;

    @Builder
    protected ResponseMemberDTO(Long memberId, String userId) {
        this.memberId = memberId;
        this.userId = userId;
    }
}
