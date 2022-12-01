package com.aimcodes.fashionBlog.exceptions;

import com.aimcodes.fashionBlog.utils.GlobalErrorMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalException {

    private final ModelMapper modelMapper;

    @ExceptionHandler(NoAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<GlobalErrorMessage> noAccessException(NoAccessException ex){

        GlobalErrorMessage errorMessage = new GlobalErrorMessage();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setDebugMsg(ex.getDebugMsg());
        errorMessage.setStatus(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);

    }


    @ExceptionHandler({HandleNullException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<GlobalErrorMessage> handleNullExceptions(HandleNullException ex){

        GlobalErrorMessage errorMessage = new GlobalErrorMessage();
        errorMessage.setDebugMsg(ex.getDebugMsg());
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NoDataFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<GlobalErrorMessage> noDataFound(NoDataFoundException ex){
        GlobalErrorMessage errorMessage = new GlobalErrorMessage();
        errorMessage.setDebugMsg(ex.getDebugMsg());
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

}
