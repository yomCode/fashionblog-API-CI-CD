package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CommentRequestDto;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    ResponseEntity<ApiResponse> create_comment(CommentRequestDto request, String uuid);

    ResponseEntity<ApiResponse> deleteComment(String uuid);

    ResponseEntity<ApiResponse> view_comment(String uuid);

    ResponseEntity<ApiResponse> view_all_comments(String uuid);
}
