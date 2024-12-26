package com.jiyunio.todolist.domain.todo;

import com.jiyunio.todolist.domain.category.Category;
import com.jiyunio.todolist.domain.category.CategoryRepository;
import com.jiyunio.todolist.domain.category.dto.CategoryRes;
import com.jiyunio.todolist.domain.member.Member;
import com.jiyunio.todolist.domain.member.MemberRepository;
import com.jiyunio.todolist.domain.todo.domain.Todo;
import com.jiyunio.todolist.domain.todo.domain.TodoList;
import com.jiyunio.todolist.domain.todo.dto.CreateTodoReq;
import com.jiyunio.todolist.domain.todo.dto.TodoListRes;
import com.jiyunio.todolist.domain.todo.dto.TodoRes;
import com.jiyunio.todolist.domain.todo.dto.UpdateTodoReq;
import com.jiyunio.todolist.global.customError.CustomException;
import com.jiyunio.todolist.global.customError.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final TodoListRepository todoListRepository;
    private final CategoryRepository categoryRepository;

    public TodoRes createTodo(String userId, CreateTodoReq createTodo) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                // 회원 존재 안함
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );
        Category category = categoryRepository.findById(createTodo.getCategoryId()).orElseThrow(
                // 카테고리 없음
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_CATEGORY)
        );

        Todo todo = Todo.builder()
                .content(createTodo.getContent())
                .checked(false)
                .categoryId(category.getId())
                .categoryContent(category.getContent())
                .categoryColor(category.getColor())
                .build();

        todoRepository.save(todo);

        TodoList todoList = todoListRepository.findByUserIdAndTodoListDate(member.getUserId(), createTodo.getSetDate()).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_TODOLIST)
        );

        todoList.getTodos().add(todo);

        return TodoRes.from(todo);
    }

    public TodoListRes createTodoList(String userId, LocalDate todoListDate) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                // 회원 존재 안함
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );

        TodoList todoList = TodoList.builder()
                .userId(member.getUserId())
                .todoListDate(todoListDate)
                .todos(new ArrayList<>())
                .build();

        todoListRepository.save(todoList);
        return TodoListRes.from(todoList);
    }

    public List<TodoListRes> getTodos(String userId) {
        List<TodoList> todoList = todoListRepository.findAllByUserId(userId);
        if (todoList == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER);
        }

        return new ArrayList<>(todoList.stream()
                .map(TodoListRes::from).toList());
    }

    public TodoRes updateTodo(Long todoId, UpdateTodoReq updateTodo) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_TODO)

        );
        Category category = categoryRepository.findById(updateTodo.getCategoryId()).get();
        todo.updateTodo(updateTodo);
        todo.updateCategory(CategoryRes.builder()
                .categoryId(category.getId())
                .color(category.getColor())
                .content(category.getContent())
                .build());

        return TodoRes.from(todo);
    }

    public void updateCategory(CategoryRes categoryDTO) {
        List<Todo> todoList = todoRepository.findByCategoryId(categoryDTO.getCategoryId());
        if (todoList.isEmpty()) {
            return;
        }
        for (Todo todo : todoList) {
            // 변경된 카테고리와 관련된 todo의 cateory도 같이 변경
            todo.updateCategory(categoryDTO);
            todoRepository.save(todo);
        }
    }

    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    public void deleteTodoList(Long todoListId) {
        todoListRepository.deleteById(todoListId);
    }

    public void deleteAllTodo(String userId) {
        todoListRepository.deleteAllByUserId(userId);
    }

}
