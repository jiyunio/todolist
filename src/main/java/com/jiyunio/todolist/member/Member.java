package com.jiyunio.todolist.member;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userId;

    private String userPw;

    private String nickname;

    private String role;

    @Builder
    private Member(String userId, String userPw, String nickname) {
        this.userId = userId;
        this.userPw = userPw;
        this.nickname = nickname;
        this.role = "ROLE_USER";
    }

    protected void updateUserPw(String userPw) {
        this.userPw = userPw;
    }

    protected void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
