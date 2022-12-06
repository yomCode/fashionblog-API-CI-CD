package com.aimcodes.fashionBlog.controllers;

import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CommentRequestDto;
import com.aimcodes.fashionBlog.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/new/{uuid}")
    public ResponseEntity<ApiResponse> create_comment(@RequestBody CommentRequestDto request, @PathVariable String uuid){
        return commentService.create_comment(request, uuid);
    }


    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<ApiResponse> delete_comment(@PathVariable String uuid){
        return commentService.deleteComment(uuid);
    }


    @GetMapping("/view/{uuid}")
    public ResponseEntity<ApiResponse> viewComment(@PathVariable String uuid){
        return commentService.view_comment(uuid);
    }

    @GetMapping("/view-all/{uuid}")
    public ResponseEntity<ApiResponse> view_all_comment(@PathVariable String uuid){
        return commentService.view_all_comments(uuid);
    }




}
