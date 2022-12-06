package com.aimcodes.fashionBlog.services;

import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.UserRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponse> createUser(UserRequestDto request);

    ResponseEntity<ApiResponse> userLogin(UserRequestDto request);

    ResponseEntity<ApiResponse> logout();
}
