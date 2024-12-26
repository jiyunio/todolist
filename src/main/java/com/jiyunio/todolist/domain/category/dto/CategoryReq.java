package com.jiyunio.todolist.domain.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@Schema(description = "category 생성 & 조회 & 수정")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryReq {
    @NotBlank
    @Schema(description = "category 내용", example = "약속")
    private final String content;

    @NotBlank
    @Schema(description = "category 색깔 (# 제외)", example = "FFFFFF")
    private final String color;

}
