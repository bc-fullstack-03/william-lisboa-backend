package com.sysmap.parrot.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateEmail {

    @Email(message = "invalid email format")
    private String email;
}