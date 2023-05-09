package com.sysmap.parrot.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthenticateRequest {
    @Email
    public String email;

    @Size(min = 8)
    @NotBlank(message = "password is mandatory")
    public String password;
}
