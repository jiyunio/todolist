package com.jiyunio.todolist.domain.todo;

import com.jiyunio.todolist.domain.todo.dto.CreateTodoReq;
import com.jiyunio.todolist.domain.todo.dto.TodoListRes;
import com.jiyunio.todolist.domain.todo.dto.TodoRes;
import com.jiyunio.todolist.domain.todo.dto.UpdateTodoReq;
import com.jiyunio.todolist.global.customError.ErrorDTO;
import com.jiyunio.todolist.global.jwt.CustomUserDetails;
import com.jiyunio.todolist.global.responseDTO.ResponseDTO;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
@Tag(name = "Todo", description = "todo API")
public class TodoController {
    private final TodoService todoService;

    @PostMapping("")
    @Operation(summary = "todo 생성")
    @ApiResponse(responseCode = "200", description = "todo 생성 성공", content = @Content(schema = @Schema(implementation = TodoRes.class)))
    @ApiResponse(responseCode = "400", description = "빈칸", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "회원 X", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<TodoRes> createTodo(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody CreateTodoReq createTodo) {
        return new ResponseEntity<>(todoService.createTodo(user.getUsername(), createTodo), HttpStatus.CREATED);
    }

    @PostMapping("/list")
    @Operation(summary = "todolist 생성")
    @ApiResponse(responseCode = "200", description = "todo 생성 성공", content = @Content(schema = @Schema(implementation = TodoListRes.class)))
    @ApiResponse(responseCode = "404", description = "회원 X", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<TodoListRes> createTodo(@AuthenticationPrincipal CustomUserDetails user, @RequestParam LocalDate todoListDate) {
        return new ResponseEntity<>(todoService.createTodoList(user.getUsername(), todoListDate), HttpStatus.CREATED);
    }

    @GetMapping("")
    @Operation(summary = "todo 조회")
    @ApiResponse(responseCode = "200", description = "todo 조회 성공", content = @Content(schema = @Schema(implementation = TodoListRes.class)))
    @ApiResponse(responseCode = "400", description = "빈칸", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "회원 X (memberId 없음)", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public List<TodoListRes> getTodo(@AuthenticationPrincipal CustomUserDetails user) {
        return todoService.getTodos(user.getUsername());
    }

    @PutMapping("/{todoId}")
    @Operation(summary = "todo 수정")
    @ApiResponse(responseCode = "200", description = "todo 수정 성공", content = @Content(schema = @Schema(implementation = TodoRes.class)))
    public ResponseEntity<TodoRes> updateTodo(@AuthenticationPrincipal CustomUserDetails user, @Parameter(description = "todo의 id") @PathVariable Long todoId, @Valid @RequestBody UpdateTodoReq updateTodo) {
        return ResponseEntity.ok(todoService.updateTodo(user.getUsername(), todoId, updateTodo));
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

    @DeleteMapping("/list/{todoListId}")
    @Operation(summary = "todo list 삭제")
    @ApiResponse(responseCode = "200", description = "todo list 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    public ResponseEntity<ResponseDTO> deleteTodoList(@Parameter(description = "todo의 id") @PathVariable Long todoListId) {
        todoService.deleteTodoList(todoListId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .msg("todolist 삭제 성공")
                .build());
    }
}
