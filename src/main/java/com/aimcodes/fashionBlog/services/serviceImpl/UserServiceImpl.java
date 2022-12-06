package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.exceptions.HandleDuplicateException;
import com.aimcodes.fashionBlog.exceptions.HandleNullException;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.UserRequestDto;
import com.aimcodes.fashionBlog.pojos.UserResponseDto;
import com.aimcodes.fashionBlog.repositories.UserRepository;
import com.aimcodes.fashionBlog.services.UserService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import com.aimcodes.fashionBlog.utils.UuidGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private HttpSession session;
    private final UserRepository userRepository;
    private final UuidGenerator uuidGenerator;

    @Override
    public ResponseEntity<ApiResponse> createUser(UserRequestDto request) {

        Boolean userExistByEmail = userRepository.existsByEmail(request.getEmail());
        Boolean userExistByUsername = userRepository.existsByUsername(request.getUsername());

        if(!userExistByEmail && !userExistByUsername) {
            String uuid = uuidGenerator.generateUuid();
            User user = User.builder()
                    .username(request.getUsername().toLowerCase())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .role(Role.USER)
                    .uuid(uuid)
                    .build();
            userRepository.save(user);

            UserResponseDto response = UserResponseDto.builder()
                    .username(user.getUsername())
                    .email(user.getEmail()).build();
            return new ResponseEntity<>(new ResponseManager().successfulRequest(response), HttpStatus.CREATED);
        }else if(userExistByEmail)
            throw new HandleDuplicateException("A user with this email already exist", "Email already registered in database");
        throw new HandleNullException("Username not available", "Username already used");
    }

    @Override
    public ResponseEntity<ApiResponse> userLogin(UserRequestDto request) {
        User user = userRepository.findByUsernameAndPassword(
                request.getUsername().toLowerCase(), request.getPassword());

        if(user != null){
            session.setAttribute("currUser", user);
            UserResponseDto response = UserResponseDto.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .build();
            return new ResponseEntity<>(new ResponseManager().successfulRequest(response), HttpStatus.OK);
        }
        throw new HandleNullException("wrong email or password", "No user found for this details");
    }

    @Override
    public ResponseEntity<ApiResponse> logout(){
        session.invalidate();
        return new ResponseEntity<>(new ResponseManager().successfulRequest("User logged out successfully"), HttpStatus.OK);
    }

}
