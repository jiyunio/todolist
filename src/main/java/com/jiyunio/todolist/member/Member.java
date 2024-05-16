package com.jiyunio.todolist.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id;

    private String userId;

    private String userPw;

    @Builder
    protected Member(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }

    protected void updateUserPw(String userPw) {
        this.userPw = userPw;
    }
}
