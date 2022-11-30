package com.aimcodes.fashionBlog.controllers;

import com.aimcodes.fashionBlog.entities.Comment;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CommentRequestDto;
import com.aimcodes.fashionBlog.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/new/{post_id}")
    public ResponseEntity<ApiResponse> create_comment(@RequestBody CommentRequestDto request, @PathVariable Long post_id, HttpSession session){
        return commentService.create_comment(request, post_id, session);
    }


    @DeleteMapping("/delete/{comment_id}")
    public ResponseEntity<ApiResponse> delete_comment(@PathVariable Long comment_id, HttpSession session){
        return commentService.deleteComment(comment_id, session);
    }


    @GetMapping("/view/{comment_id}")
    public ResponseEntity<ApiResponse> viewComment(@PathVariable Long comment_id){
        return commentService.view_comment(comment_id);
    }

    @GetMapping("/view-all/{post_id}")
    public ResponseEntity<ApiResponse> view_all_comment(@PathVariable Long post_id){
        return commentService.view_all_comments(post_id);
    }




}
