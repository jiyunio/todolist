package com.jiyunio.todolist.category;

import com.jiyunio.todolist.ResponseDTO;
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

    @PostMapping("/{member_id}")
    @Operation(summary = "카테고리 생성")
    public ResponseEntity<ResponseDTO> createCategory(@Parameter(description = "member의 id") @PathVariable Long memberId, @RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(memberId, categoryDTO);
        ResponseDTO responseDTO = ResponseDTO.builder()
                .msg("카테고리 생성 성공")
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{member_id}")
    @Operation(summary = "카테고리 조회")
    public List<CategoryDTO> getCategory(@Parameter(description = "member의 id") @PathVariable Long memberId) {
        return categoryService.getCategory(memberId);
    }

    @PutMapping("/{category_id}")
    @Operation(summary = "카테고리 수정")
    public ResponseEntity<ResponseDTO> updateCategory(@Parameter(description = "카테고리의 id") @PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryId, categoryDTO);
        ResponseDTO responseDTO = ResponseDTO.builder()
                .msg("카테고리 수정 성공")
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{category_id}")
    @Operation(summary = "카테고리 삭제")
    public ResponseEntity<ResponseDTO> deleteCategory(@Parameter(description = "카테고리의 id") @PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        ResponseDTO responseDTO = ResponseDTO.builder()
                .msg("카테고리 삭제 성공")
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.NO_CONTENT);
    }
}
