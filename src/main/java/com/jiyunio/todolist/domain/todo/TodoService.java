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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
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

        TodoList todoList = makeTodoList(member, createTodo.getSetDate());

        Todo todo = Todo.builder()
                .content(createTodo.getContent())
                .checked(false)
                .categoryId(category.getId())
                .categoryContent(category.getContent())
                .categoryColor(category.getColor())
                .todoListId(todoList.getId())
                .build();

        todoRepository.save(todo);

        return TodoRes.from(todo);
    }

    public TodoListRes createTodoList(String userId, LocalDate todoListDate) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                // 회원 존재 안함
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );
        log.warn(userId, todoListDate);
        if (todoListRepository.existsByUserIdAndTodoListDate(userId, todoListDate)) {
            throw new CustomException(HttpStatus.CONFLICT, ErrorCode.EXIST_TODOLIST);
        }

        TodoList todoList = TodoList.builder()
                .userId(member.getUserId())
                .todoListDate(todoListDate)
                .build();
        todoListRepository.save(todoList);

        return TodoListRes.from(todoList, new ArrayList<>());
    }

    public List<TodoListRes> getTodos(String userId) {
        List<TodoList> todoList = todoListRepository.findAllByUserId(userId);
        if (todoList.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_TODOLIST);
        }

        return todoList.stream().map(
                list -> {
                    List<Todo> todos = todoRepository.findAllByTodoListId(list.getId());
                    return TodoListRes.from(list, todos);
                }
        ).toList();
    }

    public TodoRes updateTodo(String userId, Long todoId, UpdateTodoReq updateTodo) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                // 회원 존재 안함
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_TODO)

        );

        TodoList todoList = makeTodoList(member, updateTodo.getSetDate());

        Category category = categoryRepository.findById(updateTodo.getCategoryId()).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_CATEGORY)
        );

        Long pastTodoListId = todo.getTodoListId();

        todo.updateTodo(updateTodo, todoList.getId());
        todo.updateCategory(CategoryRes.builder()
                .categoryId(category.getId())
                .color(category.getColor())
                .content(category.getContent())
                .build());

        //todo의 옮김으로 todolist 비었는지 확인
        checkEmptyTodoList(pastTodoListId);

        return TodoRes.from(todo);
    }

    private TodoList makeTodoList(Member member, LocalDate setDate) {
        if (!todoListRepository.existsByUserIdAndTodoListDate(member.getUserId(), setDate) && !LocalDate.now().isEqual(setDate)) {
            // 현재가 아니거나 만들어진 todolist가 없음
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_TODOLIST);
        }

        return todoListRepository.findByUserIdAndTodoListDate(member.getUserId(), setDate).orElseGet(
                () -> {
                    // 현재는 자동으로 todolist 만들어줌
                    TodoList nowTodoList = TodoList.builder()
                            .userId(member.getUserId())
                            .todoListDate(setDate)
                            .build();
                    return todoListRepository.save(nowTodoList);
                }
        );
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
        todoRepository.deleteAllByTodoListId(todoListId);
        todoListRepository.deleteById(todoListId);
    }

    // 회원 탈퇴시 모든 투두 삭제 메소드
    public void deleteAllTodo(String userId) {
        todoListRepository.deleteAllByUserId(userId);
    }

    public void checkEmptyTodoList(Long todoListId) {
        List<Todo> todos = todoRepository.findAllByTodoListId(todoListId);
        if (todos.isEmpty()) todoListRepository.deleteById(todoListId);
    }
}
