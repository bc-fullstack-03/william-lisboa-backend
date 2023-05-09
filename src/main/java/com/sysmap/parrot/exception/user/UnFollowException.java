package com.sysmap.parrot.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnFollowException extends RuntimeException {
    public UnFollowException(String message){
        super(message);
    }
}
