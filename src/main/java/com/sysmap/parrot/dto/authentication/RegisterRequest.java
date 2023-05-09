package com.sysmap.parrot.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Size(min = 5, max = 20)
    @NotBlank(message = "username is mandatory")
    public String username;

    @Email(message = "invalid email format")
    public String email;

    @Size(min = 8)
    @NotBlank(message = "password is mandatory")
    public String password;
}