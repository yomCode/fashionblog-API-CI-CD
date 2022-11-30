package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.UserRequestDto;

import javax.servlet.http.HttpSession;

public interface UserService {
    ApiResponse createUser(UserRequestDto request);

    ApiResponse userLogin(UserRequestDto request, HttpSession session);

    ApiResponse logout(HttpSession session);
}
