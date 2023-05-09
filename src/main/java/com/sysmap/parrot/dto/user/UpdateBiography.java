package com.sysmap.parrot.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateBiography {

    @NotBlank
    private String biography;
}
