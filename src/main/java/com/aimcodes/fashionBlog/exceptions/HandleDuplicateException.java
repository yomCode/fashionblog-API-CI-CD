package com.aimcodes.fashionBlog.exceptions;

import lombok.Data;

@Data
public class HandleDuplicateException extends RuntimeException{

    private String debugMsg;

    public HandleDuplicateException(String message, String debugMsg) {
        super(message);
        this.debugMsg = debugMsg;
    }
}
