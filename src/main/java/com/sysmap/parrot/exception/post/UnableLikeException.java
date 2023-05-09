package com.sysmap.parrot.exception.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UnableLikeException extends RuntimeException {
    public UnableLikeException(){
        super();
    }
}
