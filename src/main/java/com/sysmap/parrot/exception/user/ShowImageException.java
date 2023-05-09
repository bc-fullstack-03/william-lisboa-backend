package com.sysmap.parrot.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ShowImageException extends RuntimeException {
    public ShowImageException(){
        super();
    }
}
