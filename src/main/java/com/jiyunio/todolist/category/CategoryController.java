package com.jiyunio.todolist.category;

import com.jiyunio.todolist.customError.ErrorDTO;
import com.jiyunio.todolist.jwt.CustomUserDetails;
import com.jiyunio.todolist.responseDTO.ResponseCategoryDTO;
import com.jiyunio.todolist.responseDTO.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("")
    @Operation(summary = "카테고리 생성")
    @ApiResponse(responseCode = "200", description = "카테고리 생성 성공", content = @Content(schema = @Schema(implementation = ResponseCategoryDTO.class)))
    @ApiResponse(responseCode = "400", description = "빈칸", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "회원 X", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<ResponseCategoryDTO> createCategory(@AuthenticationPrincipal CustomUserDetails user, @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.createCategory(user.getUsername(), categoryDTO), HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    @Operation(summary = "카테고리 전체 조회")
    @ApiResponse(responseCode = "200", description = "카테고리 전체 조회 성공", content = @Content(schema = @Schema(implementation = ResponseCategoryDTO.class)))
    @ApiResponse(responseCode = "400", description = "빈칸", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "회원 X", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public List<ResponseCategoryDTO> getCategories(@AuthenticationPrincipal CustomUserDetails user) {
        return categoryService.getCategories(user.getUsername());
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "카테고리 수정")
    @ApiResponse(responseCode = "200", description = "카테고리 수정 성공", content = @Content(schema = @Schema(implementation = ResponseCategoryDTO.class)))
    public ResponseEntity<ResponseCategoryDTO> updateCategory(@Parameter(description = "카테고리의 id") @PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDTO));
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "카테고리 삭제")
    @ApiResponse(responseCode = "200", description = "카테고리 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    public ResponseEntity<ResponseDTO> deleteCategory(@Parameter(description = "카테고리의 id") @PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .msg("카테고리 삭제 성공")
                .build());
    }
}
