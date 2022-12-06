package com.aimcodes.fashionBlog.services;


import com.aimcodes.fashionBlog.pojos.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface LikeService {

    ResponseEntity<ApiResponse> createLike(String uuid);

    ApiResponse unlike(Long like_id);
}
