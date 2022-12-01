package com.aimcodes.fashionBlog.exceptions;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.NoSuchElementException;


@Data
@NoArgsConstructor
public class NoDataFoundException extends NullPointerException {
    private String debugMsg;

    public NoDataFoundException(String s, String debugMsg) {
        super(s);
        this.debugMsg = debugMsg;
    }


}
