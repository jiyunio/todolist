package com.jiyunio.todolist.domain.category;

import com.jiyunio.todolist.domain.category.dto.CategoryReq;
import com.jiyunio.todolist.domain.category.dto.CategoryRes;
import com.jiyunio.todolist.domain.member.Member;
import com.jiyunio.todolist.domain.member.MemberRepository;
import com.jiyunio.todolist.domain.todo.TodoListRepository;
import com.jiyunio.todolist.domain.todo.TodoRepository;
import com.jiyunio.todolist.domain.todo.TodoService;
import com.jiyunio.todolist.domain.todo.domain.TodoList;
import com.jiyunio.todolist.domain.todo.dto.TodoListRes;
import com.jiyunio.todolist.domain.todo.dto.TodoRes;
import com.jiyunio.todolist.global.customError.CustomException;
import com.jiyunio.todolist.global.customError.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final MemberRepository memberRepository;
    private final TodoListRepository todoListRepository;
    private final CategoryRepository categoryRepository;
    private final TodoService todoService;

    public CategoryRes createCategory(String userId, CategoryReq categoryReq) {
        Member member = memberRepository.findByUserId(userId).get();
        Category category = Category.builder()
                .content(categoryReq.getContent())
                .color(categoryReq.getColor())
                .userId(member.getUserId())
                .build();
        categoryRepository.save(category);

        return CategoryRes.builder()
                .categoryId(category.getId())
                .content(category.getContent())
                .color(category.getColor())
                .build();
    }

    public List<CategoryRes> getCategories(String userId) {
        List<Category> categories = categoryRepository.findByUserId(userId);
        if (categories == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER);
        }
        List<CategoryRes> getCategoryDTO = new ArrayList<>();

        for (Category category : categories) {
            getCategoryDTO.add(CategoryRes.builder()
                    .categoryId(category.getId())
                    .content(category.getContent())
                    .color(category.getColor())
                    .build());
        }
        return getCategoryDTO;
    }

    public CategoryRes updateCategory(Long categoryId, CategoryReq categoryReq) {
        Category category = categoryRepository.findById(categoryId).get();
        category.updateCategory(categoryReq);
        categoryRepository.save(category);

        //member의 category 상태도 변경
        todoService.updateCategory(CategoryRes.builder()
                .categoryId(categoryId)
                .content(categoryReq.getContent())
                .color(categoryReq.getColor()).build());


        return CategoryRes.builder()
                .categoryId(category.getId())
                .content(category.getContent())
                .color(category.getColor())
                .build();
    }

    public void deleteCategory(String userId, Long categoryId) {
        if (categoryRepository.count() == 1) {
            //카데고리 개수 >= 1
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NO_ANYMORE_CATEGORY);
        }
        // 카테고리 삭제시, 관련 todo도 함께 삭제
        List<TodoList> todoList = todoListRepository.findAllByUserIdANDCategoryId(userId, categoryId);
        todoListRepository.deleteAll(todoList);
        categoryRepository.deleteById(categoryId);
    }

    public void deleteCategories(String userId) {
        List<CategoryRes> categoryList = getCategories(userId);
        for (CategoryRes category : categoryList) {
            categoryRepository.deleteById(category.getCategoryId());
        }
    }
}
