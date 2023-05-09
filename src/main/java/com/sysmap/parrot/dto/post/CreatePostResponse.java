package com.sysmap.parrot.dto.post;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CreatePostResponse {
    private UUID id;
    private String authorId;
    private String content = "";
    private String pictureImage = "";
    private Date postedOn = new Date();
}
