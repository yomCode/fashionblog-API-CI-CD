package com.aimcodes.fashionBlog.controllers;


import com.aimcodes.fashionBlog.entities.Post;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.PostRequestDto;
import com.aimcodes.fashionBlog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/post")
public class PostController {

    private final PostService postService;


    @PostMapping("/new")
    public ApiResponse new_post(@RequestBody PostRequestDto request, HttpSession session){

        return postService.createPost(request, session);
    }

    @PatchMapping("/edit/{post_id}")
    public ApiResponse edit_post(@RequestBody PostRequestDto request, @PathVariable Long post_id, HttpSession session){

        return postService.edit_post(request, post_id, session);
    }


    @DeleteMapping("/delete/{post_id}")
    public ApiResponse delete_post(@PathVariable Long post_id, HttpSession session){

        return postService.delete_Post(post_id, session);
    }

    @GetMapping("/view-all")
    public List<Post> get_all_post(){
        return postService.view_all_post();
    }

    @GetMapping("/view-all/{category}")
    public List<Post> view_Post_by_Category(@PathVariable String category){

        return postService.view_post_by_category(category);
    }

}
