package com.jiyunio.todolist.todo;

import com.jiyunio.todolist.category.Category;
import com.jiyunio.todolist.category.CategoryRepository;
import com.jiyunio.todolist.customError.CustomException;
import com.jiyunio.todolist.customError.ErrorCode;
import com.jiyunio.todolist.member.Member;
import com.jiyunio.todolist.member.MemberRepository;
import com.jiyunio.todolist.responseDTO.ResponseCategoryDTO;
import com.jiyunio.todolist.responseDTO.ResponseTodoDTO;
import com.jiyunio.todolist.todo.dto.CreateTodoDTO;
import com.jiyunio.todolist.todo.dto.UpdateTodoDTO;
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
    private final CategoryRepository categoryRepository;

    public ResponseTodoDTO createTodo(String userId, CreateTodoDTO createTodo) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                // 회원 존재 안함
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );
        Category category = categoryRepository.findById(createTodo.getCategoryId()).orElseThrow(
                // 카테고리 없음
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_CATEGORY)
        );

        Todo todo = Todo.builder()
                .userId(member.getUserId())
                .content(createTodo.getContent())
                .setDate(createTodo.getSetDate())
                .checked(false)
                .categoryId(category.getId())
                .categoryContent(category.getContent())
                .categoryColor(category.getColor())
                .build();

        todoRepository.save(todo);

        return ResponseTodoDTO.builder()
                .todoId(todo.getId())
                .content(todo.getContent())
                .checked(todo.getChecked())
                .setDate(todo.getSetDate())
                .category(ResponseCategoryDTO.builder()
                        .categoryId(todo.getCategoryId())
                        .content(todo.getContent())
                        .color(todo.getCategoryColor())
                        .build())
                .build();
    }

    public List<ResponseTodoDTO> getTodo(String userId) {
        List<Todo> todoList = todoRepository.findByUserId(userId);
        if (todoList == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER);
        }

        List<ResponseTodoDTO> getTodoList = new ArrayList<>();

        for (Todo todo : todoList) {
            getTodoList.add(ResponseTodoDTO.builder()
                    .todoId(todo.getId())
                    .content(todo.getContent())
                    .setDate(todo.getSetDate())
                    .checked(todo.getChecked())
                    .category(ResponseCategoryDTO.builder()
                            .categoryId(todo.getCategoryId())
                            .content(todo.getCategoryContent())
                            .color(todo.getCategoryColor())
                            .build())
                    .build());
        }
        return getTodoList;
    }

    public ResponseTodoDTO updateTodo(Long todoId, UpdateTodoDTO updateTodo) {
        Todo todo = todoRepository.findById(todoId).get();
        Category category = categoryRepository.findById(updateTodo.getCategoryId()).get();
        todo.updateTodo(updateTodo);
        todo.updateCategory(ResponseCategoryDTO.builder()
                .categoryId(category.getId())
                .color(category.getColor())
                .content(category.getContent())
                .build());
        todoRepository.save(todo);

        return ResponseTodoDTO.builder()
                .todoId(todo.getId())
                .content(todo.getContent())
                .checked(todo.getChecked())
                .setDate(todo.getSetDate())
                .category(ResponseCategoryDTO.builder()
                        .categoryId(todo.getCategoryId())
                        .content(todo.getCategoryContent())
                        .color(todo.getCategoryColor())
                        .build())
                .build();
    }

    public void updateCategory(ResponseCategoryDTO categoryDTO) {
        List<Todo> todoList = todoRepository.findByCategoryId(categoryDTO.getCategoryId());
        if (todoList.isEmpty()) {
            return;
        } else {
            for (Todo todo : todoList) {
                // 변경된 카테고리와 관련된 todo의 cateory도 같이 변경
                todo.updateCategory(categoryDTO);
                todoRepository.save(todo);
            }
        }
    }

    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    public void deleteTodos(String userId) {
        List<ResponseTodoDTO> todoList = getTodo(userId);
        for (ResponseTodoDTO todo : todoList) {
            todoRepository.deleteById(todo.getTodoId());
        }
    }
}
