package com.aimcodes.fashionBlog.pojos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

//    @NotBlank(message = "username is required")
    private String username;

//    @NotBlank(message = "password is required")
    private String password;

}
