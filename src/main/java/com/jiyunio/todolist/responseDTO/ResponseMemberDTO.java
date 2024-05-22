package com.jiyunio.todolist.responseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMemberDTO {
    private Long memberId;
    private String userId;

    @Builder
    protected ResponseMemberDTO(Long memberId, String userId) {
        this.memberId = memberId;
        this.userId = userId;
    }
}
