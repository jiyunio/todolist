package com.jiyunio.todolist.responseDTO;

import com.jiyunio.todolist.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
public class ResponseTodoDTO {
    private Long todoId;
    private String content;
    private boolean checked;
    private LocalDate writeDate;
    private LocalDate setDate;
    private ResponseCategoryDTO category;

    @Builder
    protected ResponseTodoDTO(Long todoId, String content, boolean checked,
                               LocalDate writeDate, LocalDate setDate, ResponseCategoryDTO category){
        this.todoId = todoId;
        this.content = content;
        this.checked = checked;
        this.writeDate = writeDate;
        this.setDate = setDate;
        this.category = category;
    }
}
