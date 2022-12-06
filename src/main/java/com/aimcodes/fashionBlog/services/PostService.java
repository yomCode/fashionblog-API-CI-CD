package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.PostRequestDto;
import org.springframework.http.ResponseEntity;

public interface PostService {

    ResponseEntity<ApiResponse> createPost(PostRequestDto request);

    ResponseEntity<ApiResponse> edit_post(PostRequestDto request, String uuid);

    ResponseEntity<ApiResponse> delete_Post(String uuid);

    ResponseEntity<ApiResponse> view_all_post();

    ResponseEntity<ApiResponse> view_post_by_category(String categoryName);

    ResponseEntity<ApiResponse> getPostByUuid(String uuid);

    ResponseEntity<ApiResponse> searchPost(String question);
}
