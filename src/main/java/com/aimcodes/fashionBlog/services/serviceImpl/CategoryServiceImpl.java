package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.Category;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.exceptions.HandleNullException;
import com.aimcodes.fashionBlog.exceptions.NoAccessException;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CategoryRequestDto;
import com.aimcodes.fashionBlog.pojos.CategoryResponseDto;
import com.aimcodes.fashionBlog.repositories.CategoryRepository;
import com.aimcodes.fashionBlog.services.CategoryService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import com.aimcodes.fashionBlog.utils.UuidGenerator;
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
    private final UuidGenerator uuidGenerator;

    @Override
    public ResponseEntity<ApiResponse> createCategory(CategoryRequestDto request, HttpSession session) {
        User user = (User)session.getAttribute("currUser");
        if(user.getRole().equals(Role.valueOf("ADMIN"))){
            String uuid = uuidGenerator.generateUuid();
            Category category = Category.builder()
                    .name(request.getName().toLowerCase())
                    .uuid(uuid)
                    .build();
            categoryRepository.save(category);

            CategoryResponseDto response = CategoryResponseDto.builder().name(request.getName()).build();
            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.CREATED);
        }
        throw new NoAccessException("Invalid user", "User is not an admin");
    }

    @Override
    public ResponseEntity<ApiResponse> updateCategory(CategoryRequestDto request, String uuid, HttpSession session) {
        Category category = categoryRepository.findByUuid(uuid);
        User user = (User)session.getAttribute("currUser");
        if(category != null && user.getRole().equals(Role.valueOf("ADMIN"))){
            category.setName(request.getName());
            categoryRepository.save(category);

            CategoryResponseDto response = CategoryResponseDto.builder()
                    .name(category.getName())
                    .build();
            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.ACCEPTED);
        } else if (category == null)
            throw new HandleNullException("Invalid category", "Category with id " + uuid + " does not exist in database");
        throw new NoAccessException("Invalid user", "User is not an admin");
    }

    @Override
    public ResponseEntity<ApiResponse> deleteCategory(String uuid, HttpSession session) {
        Category category = categoryRepository.findByUuid(uuid);
        User user = (User)session.getAttribute("currUser");
        if(category != null && user.getRole().equals(Role.valueOf("ADMIN"))){
            categoryRepository.delete(category);

            return new ResponseEntity<>(responseManager.successfulRequest("Category deleted successful"), HttpStatus.ACCEPTED);
        } else if (category == null)
            throw new HandleNullException("Invalid category", "Category with id " + uuid + " does not exist in database");
        throw new NoAccessException("Invalid user", "User is not an admin");
    }

    @Override
    public ResponseEntity<ApiResponse> view_all_Categories() {
        List<Category> allCategories = categoryRepository.findAll();
        List<CategoryResponseDto> responses = new ArrayList<>();
        allCategories.forEach(category -> {
            CategoryResponseDto response = CategoryResponseDto.builder()
                    .name(category.getName())
                    .build();
            responses.add(response);
        });
        return new ResponseEntity<>(responseManager.successfulRequest(responses), HttpStatus.FOUND);
    }
}
