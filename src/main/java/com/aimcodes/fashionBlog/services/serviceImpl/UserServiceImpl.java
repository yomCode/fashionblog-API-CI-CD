package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.exceptions.HandleNullException;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.UserRequestDto;
import com.aimcodes.fashionBlog.pojos.UserResponseDto;
import com.aimcodes.fashionBlog.repositories.UserRepository;
import com.aimcodes.fashionBlog.services.UserService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse> createUser(UserRequestDto request) {

        User user = new User();
        user.setUsername(request.getUsername().toLowerCase());
        user.setEmail(request.getEmail().toLowerCase());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);
        userRepository.save(user);

        UserResponseDto response = new UserResponseDto();
        response.setUser_id(user.getId());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());

        return new ResponseEntity<>(new ResponseManager().successfulRequest(response), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<ApiResponse> userLogin(UserRequestDto request, HttpSession session) {

        User user = userRepository.findByUsernameAndPassword(
                request.getUsername().toLowerCase(), request.getPassword());

        if(user != null){
            session.setAttribute("currUser", user);
            UserResponseDto response = new UserResponseDto();
            response.setUser_id(user.getId());
            response.setEmail(user.getEmail());
            response.setUsername(user.getUsername());

            return new ResponseEntity<>(new ResponseManager().successfulRequest(response), HttpStatus.OK) ;
        }
        throw new HandleNullException("wrong email or password", "No user found for this details");
    }

    @Override
    public ResponseEntity<ApiResponse> logout(HttpSession session){
        session.invalidate();
        return new ResponseEntity<>(new ResponseManager().successfulRequest("User logged out successfully"), HttpStatus.OK);
    }


}
