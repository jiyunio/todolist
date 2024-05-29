package com.jiyunio.todolist.todo;

import com.jiyunio.todolist.responseDTO.ResponseDTO;
import com.jiyunio.todolist.responseDTO.ResponseTodoDTO;
import com.jiyunio.todolist.todo.dto.CreateTodoDTO;
import com.jiyunio.todolist.todo.dto.GetUpdateTodoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    public ResponseEntity<ResponseTodoDTO> createTodo(@Parameter(description = "member의 id / memberId 존재 X => NOT_FOUND_NOT_EXIST_MEMBER") @PathVariable Long memberId, @Valid @RequestBody CreateTodoDTO createTodo) {
        return new ResponseEntity<>(todoService.createTodo(memberId, createTodo), HttpStatus.CREATED);
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "todo 조회")
    public List<ResponseTodoDTO> getTodo(@Parameter(description = "member의 id / memberId 존재 X => NOT_FOUND_NOT_EXIST_MEMBER") @PathVariable Long memberId) {
        return todoService.getTodo(memberId);
    }

    @PutMapping("/{todoId}")
    @Operation(summary = "todo 수정")
    public ResponseEntity<ResponseTodoDTO> updateTodo(@Parameter(description = "todo의 id") @PathVariable Long todoId, @Valid @RequestBody GetUpdateTodoDTO updateTodo) {
        return ResponseEntity.ok(todoService.updateTodo(todoId, updateTodo));
    }

    @DeleteMapping("/{todoId}")
    @Operation(summary = "todo 삭제")
    public ResponseEntity<ResponseDTO> deleteTodo(@Parameter(description = "todo의 id") @PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .msg("todo 삭제 성공")
                .build());
    }
}
