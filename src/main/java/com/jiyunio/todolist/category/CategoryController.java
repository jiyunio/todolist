package com.jiyunio.todolist.category;

import com.jiyunio.todolist.responseDTO.ResponseCategoryDTO;
import com.jiyunio.todolist.responseDTO.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/category")
@Tag(name = "Category", description = "카테고리 API")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/{memberId}")
    @Operation(summary = "카테고리 생성")
    public ResponseEntity<ResponseCategoryDTO> createCategory(@Parameter(description = "member의 id / memberId 존재 X => NOT_FOUND_NOT_EXIST_MEMBER") @PathVariable Long memberId, @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.createCategory(memberId, categoryDTO), HttpStatus.CREATED);
    }

    @GetMapping("/categories/{memberId}")
    @Operation(summary = "카테고리 전체 조회")
    public List<ResponseCategoryDTO> getCategories(@Parameter(description = "member의 id / memberId 존재 X => NOT_FOUND_NOT_EXIST_MEMBER") @PathVariable Long memberId) {
        return categoryService.getCategories(memberId);
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "카테고리 수정")
    public ResponseEntity<ResponseCategoryDTO> updateCategory(@Parameter(description = "카테고리의 id") @PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDTO));
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "카테고리 삭제")
    public ResponseEntity<ResponseDTO> deleteCategory(@Parameter(description = "카테고리의 id") @PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .msg("카테고리 삭제 성공")
                .build());
    }
}
