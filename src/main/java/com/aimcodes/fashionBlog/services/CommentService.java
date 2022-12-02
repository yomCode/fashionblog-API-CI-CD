package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.entities.Comment;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CommentRequestDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

public interface CommentService {

    ResponseEntity<ApiResponse> create_comment(CommentRequestDto request, String uuid, HttpSession session);

    ResponseEntity<ApiResponse> deleteComment(String uuid, HttpSession session);

    ResponseEntity<ApiResponse> view_comment(String uuid);

    ResponseEntity<ApiResponse> view_all_comments(String uuid);
}
