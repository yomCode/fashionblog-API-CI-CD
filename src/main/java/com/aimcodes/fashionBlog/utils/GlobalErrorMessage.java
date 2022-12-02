package com.aimcodes.fashionBlog.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GlobalErrorMessage {

    private String message;
    private String debugMsg;
    private HttpStatus status;
}
