//package com.aimcodes.fashionBlog.services.serviceImpl;
//
//import com.aimcodes.fashionBlog.entities.User;
//import com.aimcodes.fashionBlog.pojos.UserRequestDto;
//import com.aimcodes.fashionBlog.pojos.UserResponseDto;
//import com.aimcodes.fashionBlog.repositories.UserRepository;
//import com.aimcodes.fashionBlog.services.UserService;
//import com.aimcodes.fashionBlog.utils.ResponseManager;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.servlet.http.HttpSession;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//class UserServiceImplTest {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private ResponseManager responseManager;
//    @MockBean
//    private UserRepository userRepository;
//    @Autowired
//    private HttpSession session;
//
//    @Test
//    void createUser() {
//
//        UserRequestDto request = new UserRequestDto("akeem", "ak@gmail.com", "123456");
//        User user = new User("akeem", "ak@gmail.com", "123456");
//        UserResponseDto response = new UserResponseDto("akeem", "ak@gmail.com");
//        when(userRepository.save(user)).thenReturn(user);
//        assertEquals(new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.CREATED), userService.createUser(request));
//    }
//
//    @Test
//    void userLogin() {
//        UserRequestDto request = new UserRequestDto("akeem", "123456");
//        User user = new User("akeem", "ak@gmail.com", "123456");
//        UserResponseDto response = new UserResponseDto("akeem", "ak@gmail.com");
//
//        when(userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())).thenReturn(user);
//        assertEquals(new ResponseEntity<>(new ResponseManager().successfulRequest(response), HttpStatus.OK), userService.userLogin(request));
//
//    }
//
//    @Test
//    void logout() {
//
//
//
//    }
//}