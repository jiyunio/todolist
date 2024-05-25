package com.jiyunio.todolist.responseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSignInDTO {
    private Long memberId;
    private String userId;
    private String token;

    @Builder
    protected ResponseSignInDTO(Long memberId, String userId, String token) {
        this.memberId = memberId;
        this.userId = userId;
        this.token = token;
    }
}
