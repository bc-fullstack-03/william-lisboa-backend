package com.sysmap.parrot.dto.authentication;

import lombok.Data;

@Data
public class AuthenticateResponse {
    String userId;
    String token;
}
