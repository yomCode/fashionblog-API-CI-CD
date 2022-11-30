package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.UserRequestDto;
import com.aimcodes.fashionBlog.pojos.UserResponseDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface UserService {
    ResponseEntity<ApiResponse> createUser(UserRequestDto request);

    ResponseEntity<ApiResponse> userLogin(UserRequestDto request, HttpSession session);

    ResponseEntity<ApiResponse> logout(HttpSession session);
}
