package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.entities.Comment;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CommentRequestDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

public interface CommentService {

    ResponseEntity<ApiResponse> create_comment(CommentRequestDto request, Long post_id, HttpSession session);

    ResponseEntity<ApiResponse> deleteComment(Long comment_id, HttpSession session);

    ResponseEntity<ApiResponse> view_comment(Long comment_id);

    ResponseEntity<ApiResponse> view_all_comments(Long post_id);
}
