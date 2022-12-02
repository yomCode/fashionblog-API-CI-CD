package com.aimcodes.fashionBlog.services;


import com.aimcodes.fashionBlog.pojos.ApiResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface LikeService {

    ResponseEntity<ApiResponse> createLike(Long comment_id, HttpSession session);

    ApiResponse unlike(Long like_id);
}
