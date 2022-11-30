package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.entities.Post;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.PostRequestDto;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

public interface PostService {

    ApiResponse createPost(PostRequestDto request, HttpSession session);

    ApiResponse edit_post(PostRequestDto request, Long post_id, HttpSession session);

    ApiResponse delete_Post(Long post_id, HttpSession session);

    List<Post> view_all_post();

    List<Post> view_post_by_category(String categoryName);
}
