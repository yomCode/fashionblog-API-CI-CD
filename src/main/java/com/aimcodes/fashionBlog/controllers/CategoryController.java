package com.aimcodes.fashionBlog.controllers;

import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CategoryRequestDto;
import com.aimcodes.fashionBlog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/new_category")
    public ResponseEntity<ApiResponse> newCategory(@RequestBody CategoryRequestDto request){
        return categoryService.createCategory(request);
    }


    @PatchMapping("/edit-category/{uuid}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryRequestDto request, @PathVariable String uuid){
        return categoryService.updateCategory(request, uuid);
    }

    @DeleteMapping("/delete_category/{uuid}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String uuid){
        return categoryService.deleteCategory(uuid);
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse> getCategories(){
        return categoryService.view_all_Categories();
    }


}
