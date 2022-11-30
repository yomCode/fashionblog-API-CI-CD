package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.entities.Category;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CategoryRequestDto;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryRequestDto request, HttpSession session);

    Category updateCategory(CategoryRequestDto request, Long category_id, HttpSession session);

    ApiResponse deleteCategory(Long category_id, HttpSession session);

    List<Category> view_all_Categories();

}
