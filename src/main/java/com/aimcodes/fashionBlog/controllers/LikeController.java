package com.aimcodes.fashionBlog.controllers;


import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {

    private final LikeService likeservice;


    @PostMapping("/new/{uuid}")
    public ResponseEntity<ApiResponse> likeComment(@PathVariable String uuid){

        return likeservice.createLike(uuid);
    }


    @DeleteMapping("/unlike/{like_id}")
    public ApiResponse unlike(@PathVariable Long like_id){
        return likeservice.unlike(like_id);
    }


}
