package com.sysmap.parrot.service.authentication;

import com.sysmap.parrot.dto.authentication.AuthenticateRequest;
import com.sysmap.parrot.dto.authentication.AuthenticateResponse;
import com.sysmap.parrot.dto.authentication.RegisterRequest;

public interface IAuthenticationService {
    AuthenticateResponse authenticate(AuthenticateRequest request);

    String register(RegisterRequest request);
}
