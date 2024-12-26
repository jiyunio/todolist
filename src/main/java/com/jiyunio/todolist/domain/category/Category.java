package com.jiyunio.todolist.domain.category;

import com.jiyunio.todolist.domain.category.dto.CategoryReq;
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

    private String content;

    private String color;

    private String userId;

    @Builder
    protected Category(String content, String color, String userId) {
        this.content = content;
        this.color = color;
        this.userId = userId;
    }

    protected void updateCategory(CategoryReq categoryReq) {
        this.content = categoryReq.getContent();
        this.color = categoryReq.getColor();
    }
}
