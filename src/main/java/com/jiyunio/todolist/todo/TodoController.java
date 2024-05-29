package com.jiyunio.todolist.todo;

import com.jiyunio.todolist.customError.ErrorDTO;
import com.jiyunio.todolist.responseDTO.ResponseDTO;
import com.jiyunio.todolist.responseDTO.ResponseTodoDTO;
import com.jiyunio.todolist.todo.dto.CreateTodoDTO;
import com.jiyunio.todolist.todo.dto.UpdateTodoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
@Tag(name = "Todo", description = "todo API")
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/{memberId}")
    @Operation(summary = "todo 생성", description = "todo checked 기본 값 = False")
    @ApiResponse(responseCode = "200", description = "todo 생성 성공", content = @Content(schema = @Schema(implementation = ResponseTodoDTO.class)))
    @ApiResponse(responseCode = "400", description = "빈칸", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "회원 X", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<ResponseTodoDTO> createTodo(@Parameter(description = "member의 id") @PathVariable Long memberId, @Valid @RequestBody CreateTodoDTO createTodo) {
        return new ResponseEntity<>(todoService.createTodo(memberId, createTodo), HttpStatus.CREATED);
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "todo 조회")
    @ApiResponse(responseCode = "200", description = "todo 조회 성공", content = @Content(schema = @Schema(implementation = ResponseTodoDTO.class)))
    @ApiResponse(responseCode = "400", description = "빈칸", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "회원 X (memberId 없음)", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public List<ResponseTodoDTO> getTodo(@Parameter(description = "member의 id") @PathVariable Long memberId) {
        return todoService.getTodo(memberId);
    }

    @PutMapping("/{todoId}")
    @Operation(summary = "todo 수정")
    @ApiResponse(responseCode = "200", description = "todo 수정 성공", content = @Content(schema = @Schema(implementation = ResponseTodoDTO.class)))
    public ResponseEntity<ResponseTodoDTO> updateTodo(@Parameter(description = "todo의 id") @PathVariable Long todoId, @Valid @RequestBody UpdateTodoDTO updateTodo) {
        return ResponseEntity.ok(todoService.updateTodo(todoId, updateTodo));
    }

    @DeleteMapping("/{todoId}")
    @Operation(summary = "todo 삭제")
    @ApiResponse(responseCode = "200", description = "todo 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    public ResponseEntity<ResponseDTO> deleteTodo(@Parameter(description = "todo의 id") @PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .msg("todo 삭제 성공")
                .build());
    }
}
