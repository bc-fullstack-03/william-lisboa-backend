package com.sysmap.parrot.service.post;


import com.amazonaws.services.s3.model.S3Object;
import com.sysmap.parrot.dto.post.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPostService {
    CreatePostResponse publishContent(PublishContentPost publishContentPost, String authorId);

    CreatePostResponse publishPicture(MultipartFile file, String authorId);

    S3Object getPostImage(String url);

    void updateContent(UpdateContentPost updateContentPost, String postId);

    List<GetPosts> getAllPostsByAuthor(String authorId);

    List<GetPosts> GetAllPostsFollow(String authorId);

    List<GetPosts>  getAllPosts();

    void deletePost(String id);

    void like(String postId, String userId);

    void dislike(String postId, String userId);

    void addComment(String postId, AddComment addComment, String author);

    void deleteComment(String id, String commentId);

    void updateComment(String postId, String commentId, UpdateComment updateComment);
}
