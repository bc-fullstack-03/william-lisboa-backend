package com.sysmap.parrot.model.entity;

import com.sysmap.parrot.model.embedded.Comment;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document
public class Post {
    @Id
    private String id;
    private String authorId;
    private String content = "";
    private String pictureImage = "";
    private Date postedOn = new Date();
    private List<String> likes = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
}
