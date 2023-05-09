package com.sysmap.parrot.controller;

import com.sysmap.parrot.dto.authentication.AuthenticateRequest;
import com.sysmap.parrot.dto.authentication.AuthenticateResponse;
import com.sysmap.parrot.dto.authentication.RegisterRequest;
import com.sysmap.parrot.service.authentication.impl.AuthenticationServiceImpl;
import com.sysmap.parrot.service.user.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;
    private final UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(
            @Valid @RequestBody AuthenticateRequest request
    ) {
        return ResponseEntity.ok().body(authenticationService.authenticate(request));
    }
}
