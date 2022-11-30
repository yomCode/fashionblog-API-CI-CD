package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.UserRequestDto;
import com.aimcodes.fashionBlog.repositories.UserRepository;
import com.aimcodes.fashionBlog.services.UserService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
//    private final ResponseManager responseManager;

    @Override
    public ApiResponse createUser(UserRequestDto request) {

        User user = new User();

        user.setUsername(request.getUsername().toLowerCase());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);

        userRepository.save(user);

        return new ApiResponse("Request successful", true, user);

    }

    @Override
    public ApiResponse userLogin(UserRequestDto request, HttpSession session) {

        User user = userRepository.findByUsernameAndPassword(
                request.getUsername().toLowerCase(), request.getPassword());



        if(user != null){
            session.setAttribute("currUser", user);
            return new ResponseManager().successfulRequest(user);
        }

        return new ResponseManager().failedRequest();
    }

    @Override
    public ApiResponse logout(HttpSession session){
        session.invalidate();
        return new ResponseManager().successfulRequest("User logged out successfully");
    }


}
