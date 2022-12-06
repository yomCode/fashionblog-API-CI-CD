package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.Category;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.exceptions.NoDataFoundException;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CategoryRequestDto;
import com.aimcodes.fashionBlog.pojos.UserRequestDto;
import com.aimcodes.fashionBlog.repositories.CategoryRepository;
import com.aimcodes.fashionBlog.repositories.UserRepository;
import com.aimcodes.fashionBlog.services.CategoryService;
import com.aimcodes.fashionBlog.services.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.FOUND;

@SpringBootTest
class CategoryServiceImplTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        User user = new User("Dele", "deee@gmail.com", "123456", Role.ADMIN);
        UserRequestDto request = new UserRequestDto("dele", "123456");
        Mockito.when(userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())).thenReturn(user);
        userService.userLogin(request);

        Category category = new Category("1HWB4J2KSJ","Gucci");
        Mockito.when(categoryRepository.findByUuid("1HWB4J2KSJ")).thenReturn(Optional.of(category));
    }

    @Test
    void createCategory() {
        CategoryRequestDto request = new CategoryRequestDto("balanciaga");
        ResponseEntity<ApiResponse> category = categoryService.createCategory(request);

        assertEquals(201, category.getStatusCodeValue());
    }

    @Test
    void updateCategory_success() {
        CategoryRequestDto request = new CategoryRequestDto("new_name");
        categoryService.updateCategory(request, "1HWB4J2KSJ");
        Category category = categoryRepository.findByUuid("1HWB4J2KSJ").orElseThrow(() ->
                new NoDataFoundException("Category does not exist", "Enter a valid category name"));

        assertEquals("new_name", category.getName());
    }

    @Test
    void deleteCategory() {
        ResponseEntity<ApiResponse> deletedCategory = categoryService.deleteCategory("1HWB4J2KSJ");

        assertEquals(202, deletedCategory.getStatusCodeValue());
    }

    @Test
    void view_all_Categories() {
        ResponseEntity<ApiResponse> categories = categoryService.view_all_Categories();
        assertEquals(302, categories.getStatusCodeValue());
    }
}