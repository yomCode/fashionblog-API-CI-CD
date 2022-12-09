package com.aimcodes.fashionBlog.utils;

import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.pojos.UserRequestDto;

public class DataTransfer {

    public User dtoToModel(UserRequestDto from) {

        User user = User.builder().email(from.getEmail())
                        .username(from.getUsername())
                                .password(from.getPassword())
                                        .build();

        return user;
    }

}
