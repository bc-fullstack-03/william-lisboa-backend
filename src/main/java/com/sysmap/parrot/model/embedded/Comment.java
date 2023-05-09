package com.sysmap.parrot.model.embedded;

import lombok.Data;

@Data
public class Comment {
    private String id;
    private String userId;
    private String content;
}