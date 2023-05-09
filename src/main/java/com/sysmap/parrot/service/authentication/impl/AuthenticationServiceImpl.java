package com.sysmap.parrot.service.authentication.impl;

import com.sysmap.parrot.dto.authentication.AuthenticateRequest;
import com.sysmap.parrot.dto.authentication.AuthenticateResponse;
import com.sysmap.parrot.dto.authentication.RegisterRequest;
import com.sysmap.parrot.exception.authentication.InvalidCredentialException;
import com.sysmap.parrot.jwt.JwtTokenUtil;
import com.sysmap.parrot.service.authentication.IAuthenticationService;
import com.sysmap.parrot.service.user.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UserServiceImpl userService;

    private final JwtTokenUtil jwtService;

    private final PasswordEncoder passwordEncoder;


    public String register(RegisterRequest request){
        return userService.create(request);
    }

    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        var user = userService.findByEmail(request.email);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidCredentialException();
        }

        var token = jwtService.generateToken(user.getId());

        var response = new AuthenticateResponse();
        response.setUserId(user.getId());
        response.setToken(token);

        return response;
    }
}
