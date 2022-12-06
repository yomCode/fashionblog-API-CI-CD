package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CategoryRequestDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseEntity<ApiResponse> createCategory(CategoryRequestDto request);

    ResponseEntity<ApiResponse> updateCategory(CategoryRequestDto request, String uuid);

    ResponseEntity<ApiResponse> deleteCategory(String uuid);

    ResponseEntity<ApiResponse> view_all_Categories();

}
