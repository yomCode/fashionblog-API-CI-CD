package com.aimcodes.fashionBlog.utils;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

@Component
public class UuidGenerator {

    private final Random random = new SecureRandom();
    private final String alphabets = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generateUuid(){
        return generateRandomString();
    }


    private String generateRandomString(){
        UUID.randomUUID().toString();
        int length = 10;
        StringBuilder returnValue = new StringBuilder(length);

        for(int i = 0; i< length; i++){
            returnValue.append(alphabets.charAt(random.nextInt(alphabets.length())));
        }
        return new String(returnValue);
    }

}
