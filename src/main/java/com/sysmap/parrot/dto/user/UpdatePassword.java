package com.sysmap.parrot.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePassword {
    @Size(min = 8)
    @NotBlank(message = "password is mandatory")
    private String password;
}