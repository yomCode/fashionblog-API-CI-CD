package com.aimcodes.fashionBlog.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoAccessException extends RuntimeException{

    private String debugMsg;

    public NoAccessException(String message) {
        super(message);
    }

    public NoAccessException(String message, String debugMsg) {
        super(message);
        this.debugMsg = debugMsg;
    }
}
