package com.jiyunio.todolist.domain.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "todo 수정")
public class UpdateTodoReq {
    @NotBlank(message = "todo를 작성해주세요.")
    @Schema(description = "todo 내용", example = "친구랑 홍대")
    private final String content;

    @NotNull(message = "설정 일자를 선택해주세요.")
    @Schema(description = "todo 설정 일자", example = "YYYY-MM-DD")
    private final LocalDate setDate;

    @NotNull
    @Schema(description = "category Id", example = "1")
    private final Long categoryId;

    @NotNull
    @Schema(description = "todo checked", example = "true")
    private final Boolean checked;
}
