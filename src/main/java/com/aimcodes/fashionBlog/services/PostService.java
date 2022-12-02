package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.entities.Post;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.PostRequestDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface PostService {

    ResponseEntity<ApiResponse> createPost(PostRequestDto request, HttpSession session);

    ResponseEntity<ApiResponse> edit_post(PostRequestDto request, String uuid, HttpSession session);

    ResponseEntity<ApiResponse> delete_Post(String uuid, HttpSession session);

    ResponseEntity<ApiResponse> view_all_post();

    ResponseEntity<ApiResponse> view_post_by_category(String categoryName);
}
