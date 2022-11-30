package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.Category;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CategoryRequestDto;
import com.aimcodes.fashionBlog.pojos.CategoryResponseDto;
import com.aimcodes.fashionBlog.pojos.CommentResponseDto;
import com.aimcodes.fashionBlog.repositories.CategoryRepository;
import com.aimcodes.fashionBlog.services.CategoryService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ResponseManager responseManager;

    @Override
    public ResponseEntity<ApiResponse> createCategory(CategoryRequestDto request, HttpSession session) {
        User user = (User)session.getAttribute("currUser");
        if(user.getRole().equals(Role.valueOf("ADMIN"))){
            Category category = new Category();
            category.setName(request.getName().toLowerCase());
            categoryRepository.save(category);

            CategoryResponseDto response = new CategoryResponseDto();
            response.setName(category.getName());

            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.CREATED);
        }
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updateCategory(CategoryRequestDto request, Long category_id, HttpSession session) {
        Category category = categoryRepository.findById(category_id).orElseThrow(null);
        User user = (User)session.getAttribute("currUser");
        CategoryResponseDto response = new CategoryResponseDto();
        if(user.getRole().equals(Role.valueOf("ADMIN"))){
            category.setName(request.getName());
            categoryRepository.save(category);

            response.setName(category.getName());
        }
        return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteCategory(Long category_id, HttpSession session) {
        Category category = categoryRepository.findById(category_id).orElseThrow(null);
        User user = (User)session.getAttribute("currUser");
        if(user.getRole().equals(Role.valueOf("ADMIN"))){
            categoryRepository.delete(category);

            return new ResponseEntity<>(responseManager.successfulRequest("Category deleted successful"), HttpStatus.ACCEPTED);
        }
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> view_all_Categories() {
        List<Category> allCategories = categoryRepository.findAll();
        List<CategoryResponseDto> responses = new ArrayList<>();

        allCategories.forEach(category -> {

            CategoryResponseDto response = new CategoryResponseDto();
            response.setName(category.getName());
            responses.add(response);

        });
        return new ResponseEntity<>(responseManager.successfulRequest(responses), HttpStatus.FOUND);
    }
}
