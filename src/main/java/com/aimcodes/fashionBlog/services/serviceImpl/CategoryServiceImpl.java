package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.Category;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CategoryRequestDto;
import com.aimcodes.fashionBlog.repositories.CategoryRepository;
import com.aimcodes.fashionBlog.services.CategoryService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ResponseManager responseManager;

    @Override
    public Category createCategory(CategoryRequestDto request, HttpSession session) {
        User user = (User)session.getAttribute("currUser");
        if(user.getRole().equals(Role.valueOf("ADMIN"))){
            Category category = new Category();
            category.setName(request.getName().toLowerCase());

            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public Category updateCategory(CategoryRequestDto request, Long category_id, HttpSession session) {
        Category category = categoryRepository.findById(category_id).orElseThrow(null);
        User user = (User)session.getAttribute("currUser");
        if(user.getRole().equals(Role.valueOf("ADMIN"))){
            category.setName(request.getName());

            categoryRepository.save(category);
        }

        return category;
    }

    @Override
    public ApiResponse deleteCategory(Long category_id, HttpSession session) {
        Category category = categoryRepository.findById(category_id).orElseThrow(null);
        User user = (User)session.getAttribute("currUser");
        if(user.getRole().equals(Role.valueOf("ADMIN"))){
            categoryRepository.delete(category);

            return responseManager.successfulRequest("Category deleted successful");
        }
        return null;
    }

    @Override
    public List<Category> view_all_Categories() {
        List<Category> allCategories = categoryRepository.findAll();
        return allCategories;
    }
}
