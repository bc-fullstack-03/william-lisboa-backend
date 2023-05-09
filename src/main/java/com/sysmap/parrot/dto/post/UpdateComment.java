package com.sysmap.parrot.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateComment {
    @NotNull(message = "content may not be null")
    private String content;
}
