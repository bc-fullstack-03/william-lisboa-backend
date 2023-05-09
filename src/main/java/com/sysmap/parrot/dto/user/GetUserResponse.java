package com.sysmap.parrot.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class GetUserResponse {
    private String id;
    private String username;
    private String email;
    private String profileImage;
    private String biography;
    private List<String> following;
    private List<String> followers;
}