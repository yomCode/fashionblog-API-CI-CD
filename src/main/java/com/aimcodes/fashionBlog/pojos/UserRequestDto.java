package com.aimcodes.fashionBlog.pojos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotNull(message = "username cannot be empty")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 8 characters")
    private String username;

    @NotBlank(message = "Email address is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Enter password")
    @Size(min = 6, max = 20, message = "Password must be at least 6 characters long")
    private String password;

    public UserRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
