package com.jiyunio.todolist.category;

import com.jiyunio.todolist.customError.CustomException;
import com.jiyunio.todolist.customError.ErrorCode;
import com.jiyunio.todolist.member.Member;
import com.jiyunio.todolist.member.MemberRepository;
import com.jiyunio.todolist.responseDTO.ResponseCategoryDTO;
import com.jiyunio.todolist.todo.TodoRepository;
import com.jiyunio.todolist.todo.TodoService;
import com.jiyunio.todolist.todo.dto.CreateTodoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final TodoService todoService;

    public ResponseCategoryDTO createCategory(Long memberId, CategoryDTO categoryDTO) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                // 회원 존재 안함
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EXIST_MEMBER)
        );


        Category category = Category.builder()
                .member(member)
                .content(categoryDTO.getContent())
                .color(categoryDTO.getColor())
                .build();
        categoryRepository.save(category);

        return ResponseCategoryDTO.builder()
                .categoryId(category.getId())
                .content(category.getContent())
                .color(category.getColor())
                .build();
    }

    public List<ResponseCategoryDTO> getCategories(Long memberId) {
        List<Category> categories = categoryRepository.findByMemberId(memberId);
        List<ResponseCategoryDTO> getCategoryDTO = new ArrayList<>();

        for (Category category : categories) {
            getCategoryDTO.add(ResponseCategoryDTO.builder()
                    .categoryId(category.getId())
                    .content(category.getContent())
                    .color(category.getColor())
                    .build());
        }
        return getCategoryDTO;
    }

    public ResponseCategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId).get();
        category.updateCategory(categoryDTO);
        categoryRepository.save(category);
        todoService.updateCategory(categoryId, CategoryDTO.builder() //member의 category 상태도 변경
                .content(category.getContent())
                .color(category.getColor())
                .build());

        return ResponseCategoryDTO.builder()
                .categoryId(category.getId())
                .content(category.getContent())
                .color(category.getColor())
                .build();
    }

    public void deleteCategory(Long categoryId) {
        if (categoryRepository.count() == 1) {
            //카데고리 개수 >= 1
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NO_ANYMORE_CATEGORY);
        } else {
            categoryRepository.deleteById(categoryId);
        }
    }
}
