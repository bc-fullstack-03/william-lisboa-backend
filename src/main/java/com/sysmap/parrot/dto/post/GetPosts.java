package com.sysmap.parrot.dto.post;

import com.sysmap.parrot.model.embedded.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class GetPosts {
    private UUID id;
    private String authorId;
    private String content;
    private String pictureImage;
    private Date postedOn;
    private List<String> likes;
    private List<Comment> comments;
}
