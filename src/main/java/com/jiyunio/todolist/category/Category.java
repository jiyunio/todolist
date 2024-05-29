package com.jiyunio.todolist.category;

import com.jiyunio.todolist.member.Member;
import com.jiyunio.todolist.todo.Todo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "todoId")
    List<Todo> todo;

    @Builder
    protected Category(Member member, String content, String color, List<Todo> todo) {
        this.member = member;
        this.content = content;
        this.color = color;
        this.todo = todo;
    }

    protected void updateCategory(CategoryDTO categoryDTO) {
        this.content = categoryDTO.getContent();
        this.color = categoryDTO.getColor();
    }
}
