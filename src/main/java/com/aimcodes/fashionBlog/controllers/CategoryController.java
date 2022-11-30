package com.aimcodes.fashionBlog.controllers;

import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CategoryRequestDto;
import com.aimcodes.fashionBlog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/new_category")
    public ResponseEntity<ApiResponse> newCategory(@RequestBody CategoryRequestDto request, HttpSession session){
        return categoryService.createCategory(request, session);
    }


    @PatchMapping("/edit-category/{category_id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryRequestDto request, @PathVariable Long category_id, HttpSession session){
        return categoryService.updateCategory(request, category_id, session);
    }

    @DeleteMapping("/delete_category/{category_id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long category_id, HttpSession session){
        return categoryService.deleteCategory(category_id, session);
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse> getCategories(){
        return categoryService.view_all_Categories();
    }


}
