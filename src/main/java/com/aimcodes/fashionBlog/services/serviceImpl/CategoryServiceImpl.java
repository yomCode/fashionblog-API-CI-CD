package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.Category;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.exceptions.HandleNullException;
import com.aimcodes.fashionBlog.exceptions.NoAccessException;
import com.aimcodes.fashionBlog.exceptions.NoDataFoundException;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CategoryRequestDto;
import com.aimcodes.fashionBlog.pojos.CategoryResponseDto;
import com.aimcodes.fashionBlog.repositories.CategoryRepository;
import com.aimcodes.fashionBlog.services.CategoryService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import com.aimcodes.fashionBlog.utils.UuidGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private HttpSession session;
    private final CategoryRepository categoryRepository;
    private final ResponseManager responseManager;
    private final UuidGenerator uuidGenerator;

    @Override
    public ResponseEntity<ApiResponse> createCategory(CategoryRequestDto request) {
        User user = (User)session.getAttribute("currUser");
        if(user != null && user.getRole().equals(Role.valueOf("ADMIN"))){
            String uuid = uuidGenerator.generateUuid();
            Category category = Category.builder()
                    .name(request.getName().toLowerCase())
                    .uuid(uuid)
                    .build();
            categoryRepository.save(category);

            CategoryResponseDto response = CategoryResponseDto.builder().name(request.getName()).build();
            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.CREATED);
        } else if (user == null)
            throw new HandleNullException("Invalid user request", "No user in session");
        throw new NoAccessException("Invalid user", "User is not an admin");
    }

    @Override
    public ResponseEntity<ApiResponse> updateCategory(CategoryRequestDto request, String uuid) {
        Category category = categoryRepository.findByUuid(uuid).orElseThrow(() ->
                new NoDataFoundException("Category does not exist", "Enter a valid category name"));
        User user = (User)session.getAttribute("currUser");
        if(user != null && user.getRole().equals(Role.valueOf("ADMIN"))){
            category.setName(request.getName());
            categoryRepository.save(category);

            CategoryResponseDto response = CategoryResponseDto.builder()
                    .name(category.getName())
                    .build();
            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.ACCEPTED);
        } else if (user == null)
            throw new HandleNullException("Invalid user request", "No user in session");
        throw new NoAccessException("Invalid user", "User is not an admin");
    }

    @Override
    public ResponseEntity<ApiResponse> deleteCategory(String uuid) {
        Category category = categoryRepository.findByUuid(uuid).orElseThrow(() ->
                new NoDataFoundException("Category does not exist", "Enter a valid category name"));
        User user = (User)session.getAttribute("currUser");
        if(user != null && user.getRole().equals(Role.valueOf("ADMIN"))){
            categoryRepository.delete(category);

            return new ResponseEntity<>(responseManager.successfulRequest("Category deleted successful"), HttpStatus.ACCEPTED);
        } else if (user == null)
            throw new HandleNullException("Invalid user request", "No user in session");
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
