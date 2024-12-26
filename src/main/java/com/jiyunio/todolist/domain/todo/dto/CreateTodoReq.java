package com.jiyunio.todolist.domain.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "todo 생성 : todo checked 기본 값 False")
public class CreateTodoReq {
    @NotBlank(message = "todo를 작성해주세요.")
    @Schema(description = "todo 내용", example = "친구랑 치킨집")
    private String content;

    @NotNull
    @Schema(description = "category Id", example = "1")
    private Long categoryId;

    @NotNull(message = "설정 일자를 선택해주세요.")
    @Schema(description = "todo 설정 일자", example = "YYYY-MM-DD")
    private LocalDate setDate;

}
