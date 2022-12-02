package com.aimcodes.fashionBlog.controllers;


import com.aimcodes.fashionBlog.entities.Post;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.PostRequestDto;
import com.aimcodes.fashionBlog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/post")
public class PostController {

    private final PostService postService;


    @PostMapping("/new")
    public ResponseEntity<ApiResponse> new_post(@RequestBody PostRequestDto request, HttpSession session){

        return postService.createPost(request, session);
    }

    @PutMapping("/edit/{uuid}")
    public ResponseEntity<ApiResponse> edit_post(@RequestBody PostRequestDto request, @PathVariable String uuid, HttpSession session){

        return postService.edit_post(request, uuid, session);
    }


    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<ApiResponse> delete_post(@PathVariable String uuid, HttpSession session){

        return postService.delete_Post(uuid, session);
    }

    @GetMapping("/view-all")
    public ResponseEntity<ApiResponse> get_all_post(){
        return postService.view_all_post();
    }

    @GetMapping("/view-all/{uuid}")
    public ResponseEntity<ApiResponse> view_Post_by_Category(@PathVariable String uuid){

        return postService.view_post_by_category(uuid);
    }

}
