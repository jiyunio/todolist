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

    @Column(columnDefinition = "text")
    private String userId;

    private String userPw;

    private String role;

    @Builder
    private Member(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
        this.role = "ROLE_USER";
    }

    protected void updateUserPw(String userPw) {
        this.userPw = userPw;
    }
}
