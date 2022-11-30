package com.aimcodes.fashionBlog.utils;

import com.aimcodes.fashionBlog.pojos.ApiResponse;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ResponseManager  {

    public ApiResponse successfulRequest(Object data){

        return new ApiResponse<>("Request successful", true, data);
    }


    public ApiResponse failedRequest(){

        return new ApiResponse<>("Request unsuccessful", false, null);
    }

}
