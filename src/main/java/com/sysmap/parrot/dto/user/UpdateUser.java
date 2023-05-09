package com.sysmap.parrot.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUser {
    @Size(min = 5, max = 20)
    @NotBlank(message = "username is mandatory")
    public String username;

    @Email(message = "invalid email format")
    public String email;

    @NotBlank
    private String biography;
}
