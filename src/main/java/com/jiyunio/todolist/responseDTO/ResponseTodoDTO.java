package com.jiyunio.todolist.responseDTO;

import com.jiyunio.todolist.category.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
public class ResponseTodoDTO {
    @NotNull
    private Long todoId;

    @NotBlank
    private String content;

    private boolean checked;

    @NotNull
    private LocalDate writeDate;

    @NotNull
    private LocalDate setDate;

    @NotNull
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
