package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CategoryRequestDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface CategoryService {

    ResponseEntity<ApiResponse> createCategory(CategoryRequestDto request, HttpSession session);

    ResponseEntity<ApiResponse> updateCategory(CategoryRequestDto request, Long category_id, HttpSession session);

    ResponseEntity<ApiResponse> deleteCategory(Long category_id, HttpSession session);

    ResponseEntity<ApiResponse> view_all_Categories();

}
