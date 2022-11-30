package com.aimcodes.fashionBlog.pojos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponseDto {

    private String title;
    private String content;
    private String category;
    private Date created_date;
}
