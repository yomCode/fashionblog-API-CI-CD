package com.aimcodes.fashionBlog.services;


import com.aimcodes.fashionBlog.pojos.ApiResponse;

import javax.servlet.http.HttpSession;

public interface LikeService {

    ApiResponse createLike(Long comment_id, HttpSession session);

    ApiResponse unlike(Long like_id);
}
