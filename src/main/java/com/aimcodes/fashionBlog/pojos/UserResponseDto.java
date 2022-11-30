package com.aimcodes.fashionBlog.pojos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {

    private Long user_id;
    private String Username;
    private String email;

}
