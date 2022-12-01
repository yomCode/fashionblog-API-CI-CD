package com.aimcodes.fashionBlog.exceptions;


import lombok.Data;

@Data
public class HandleNullException extends NullPointerException{

    private String debugMsg;

    public HandleNullException(String s, String debugMsg) {
        super(s);
        this.debugMsg = debugMsg;
    }

    public HandleNullException(String s) {
        super(s);
    }
}
