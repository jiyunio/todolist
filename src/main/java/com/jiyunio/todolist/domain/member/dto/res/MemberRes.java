package com.jiyunio.todolist.domain.member.dto.res;

import com.jiyunio.todolist.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRes {
    @NotNull
    @Schema(description = "회원의 Id", example = "1")
    private Long memberId;

    @NotBlank
    @Schema(description = "회원의 userId", example = "jiyun123")
    private String userId;

    @NotBlank
    @Schema(description = "회원의 userId", example = "jiyun123")
    private String nickname;

    @Builder
    private MemberRes(Long memberId, String userId, String nickname) {
        this.memberId = memberId;
        this.userId = userId;
        this.nickname = nickname;
    }

    public static MemberRes from(Member member) {
        return MemberRes.builder()
                .memberId(member.getId())
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .build();
    }
}
