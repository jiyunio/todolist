package com.jiyunio.todolist.todo;

import com.jiyunio.todolist.customError.CustomException;
import com.jiyunio.todolist.customError.ErrorCode;
import com.jiyunio.todolist.member.Member;
import com.jiyunio.todolist.member.MemberRepository;
import com.jiyunio.todolist.responseDTO.ResponseTodoDTO;
import com.jiyunio.todolist.todo.dto.CreateTodoDTO;
import com.jiyunio.todolist.todo.dto.GetUpdateTodoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;

    public ResponseTodoDTO createTodo(Long memberId, CreateTodoDTO createTodo) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                // 회원 존재 안함
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );

        Todo todo = Todo.builder()
                .member(member)
                .content(createTodo.getContent())
                .writeDate(createTodo.getWriteDate())
                .setDate(createTodo.getSetDate())
                .checked(false)
                .categoryId(createTodo.getCategoryId())
                .build();
        todoRepository.save(todo);

        return ResponseTodoDTO.builder()
                .todoId(todo.getId())
                .content(todo.getContent())
                .checked(todo.getChecked())
                .writeDate(todo.getWriteDate())
                .setDate(todo.getSetDate())
                .categoryId(todo.getCategoryId())
                .build();
    }

    public List<ResponseTodoDTO> getTodo(Long memberId) {
        List<Todo> todoList = todoRepository.findByMemberId(memberId);
        List<ResponseTodoDTO> getTodoList = new ArrayList<>();

        for (Todo todo : todoList) {
            getTodoList.add(ResponseTodoDTO.builder()
                    .todoId(todo.getId())
                    .content(todo.getContent())
                    .writeDate(todo.getWriteDate())
                    .setDate(todo.getSetDate())
                    .checked(todo.getChecked())
                    .categoryId(todo.getCategoryId())
                    .build());
        }
        return getTodoList;
    }

    public ResponseTodoDTO updateTodo(Long todoId,GetUpdateTodoDTO updateTodo) {
        Todo todo = todoRepository.findById(todoId).get();
        todo.updateTodo(updateTodo);
        todoRepository.save(todo);

        return ResponseTodoDTO.builder()
                .todoId(todo.getId())
                .content(todo.getContent())
                .checked(todo.getChecked())
                .writeDate(todo.getWriteDate())
                .setDate(todo.getSetDate())
                .categoryId(todo.getCategoryId())
                .build();
    }

    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}
