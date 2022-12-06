package com.aimcodes.fashionBlog.controllers;


import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.PostRequestDto;
import com.aimcodes.fashionBlog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponse> new_post(@RequestBody PostRequestDto request){

        return postService.createPost(request);
    }

    @PutMapping("/edit/{uuid}")
    public ResponseEntity<ApiResponse> edit_post(@RequestBody PostRequestDto request, @PathVariable String uuid){

        return postService.edit_post(request, uuid);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<ApiResponse> delete_post(@PathVariable String uuid){

        return postService.delete_Post(uuid);
    }

    @GetMapping("/view-all")
    public ResponseEntity<ApiResponse> get_all_post(){
        return postService.view_all_post();
    }

    @GetMapping("/view-all/{uuid}")
    public ResponseEntity<ApiResponse> view_Post_by_Category(@PathVariable String uuid){

        return postService.view_post_by_category(uuid);
    }

    @GetMapping("/view/{uuid}")
    public ResponseEntity<ApiResponse> getPostByUuid(@PathVariable String uuid){
        return postService.getPostByUuid(uuid);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> findPost(@RequestParam("q") String question){

        return postService.searchPost(question);
    }

}
