package com.jiyunio.todolist.category;

import com.jiyunio.todolist.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    private String content;

    private String color;

    @Builder
    protected Category(Member member, String content, String color) {
        this.member = member;
        this.content = content;
        this.color = color;
    }

    protected void updateCategory(CategoryDTO categoryDTO) {
        this.content = categoryDTO.getContent();
        this.color = categoryDTO.getColor();
    }
}
