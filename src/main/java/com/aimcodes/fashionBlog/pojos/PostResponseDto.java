package com.aimcodes.fashionBlog.pojos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostResponseDto {

    private String title;
    private String content;
    private String category;
    private Date created_date;
}
