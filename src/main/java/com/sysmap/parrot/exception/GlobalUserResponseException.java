package com.sysmap.parrot.exception;

import com.sysmap.parrot.exception.authentication.InvalidCredentialException;
import com.sysmap.parrot.exception.post.PostNotFoundException;
import com.sysmap.parrot.exception.post.UnableDislikeException;
import com.sysmap.parrot.exception.post.UnableLikeException;
import com.sysmap.parrot.exception.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalUserResponseException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(){

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "User Not Found");

        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<ApiError> handleInvalidUsernameException(){

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Invalid Username");

        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ApiError> handleInvalidEmailException(){

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Invalid email");

        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler(UnableFollowUserException.class)
    public ResponseEntity<ApiError> handleUnableFollowUserException(){

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "You were unable to follow this user");

        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler(FollowException.class)
    public ResponseEntity<ApiError> handleFollowException(FollowException ex){

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );

        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler(UnFollowException.class)
    public ResponseEntity<ApiError> handleUnFollowException(UnFollowException ex){

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );

        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler(ShowImageException.class)
    public ResponseEntity<ApiError> handleShowImageException(){

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.EXPECTATION_FAILED,
                "could not complete the operation"
        );

        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ApiError> handleInvalidCredentialException(){

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "invalid credentials"
        );

        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiError> handlePostNotFoundException(){

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Post not found"
        );

        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler(UnableLikeException.class)
    public ResponseEntity<ApiError> handleUnableLikeException(){

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.EXPECTATION_FAILED,
                "unable to like the post"
        );

        return new ResponseEntity<>(error,error.getStatus());
    }

    @ExceptionHandler(UnableDislikeException.class)
    public ResponseEntity<ApiError> handleUnableDislikeException(){

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.EXPECTATION_FAILED,
                "unable to dislike the post"
        );

        return new ResponseEntity<>(error,error.getStatus());
    }

}
