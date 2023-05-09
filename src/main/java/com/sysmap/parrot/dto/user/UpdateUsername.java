package com.sysmap.parrot.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUsername {
    @Size(min = 5, max = 20)
    @NotBlank(message = "username is mandatory")
    private String username;
}