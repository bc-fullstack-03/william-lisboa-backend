package com.sysmap.parrot.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.sysmap.parrot.dto.post.*;
import com.sysmap.parrot.exception.user.ShowImageException;
import com.sysmap.parrot.service.post.impl.PostServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping("/publish/content")
    public ResponseEntity<CreatePostResponse> postContent(
            @Valid  @RequestBody PublishContentPost publishContentPost,
            HttpServletRequest request) {

        String authorId = request.getHeader("userId");
        var response = postService.publishContent(publishContentPost, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/publish/picture")
    public ResponseEntity<CreatePostResponse> postPicture(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request){

        String authorId = request.getHeader("userId");

        var response = postService.publishPicture(file, authorId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/image/{filepath}")
    public ResponseEntity<byte[]> getPostImage(
            @Valid @PathVariable String filepath) {

        try {
            S3Object s3Object = postService.getPostImage(filepath);
            String contentType = s3Object.getObjectMetadata().getContentType();
            var bytes = s3Object.getObjectContent().readAllBytes();

            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.valueOf(contentType));
            header.setContentLength(bytes.length);

            return ResponseEntity.ok().headers(header).body(bytes);
        }catch (IOException ex){
            throw new ShowImageException();
        }
    }

    @PatchMapping("/{id}/content")
    public ResponseEntity updateContent(
            @Valid @PathVariable String id,
            @Valid  @RequestBody UpdateContentPost updateContent) {

        postService.updateContent(updateContent, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{author}")
    public ResponseEntity<List<GetPosts>> getAllPostsByAuthor(@Valid @PathVariable String author) {
        var posts = postService.getAllPostsByAuthor(author);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/following/{author}")
    public ResponseEntity<List<GetPosts>> getAllPostsFollow(@Valid @PathVariable String author) {
        var posts = postService.GetAllPostsFollow(author);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping
    public ResponseEntity<List<GetPosts>> getAllPosts() {
        var posts = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@Valid @PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/like")
    public ResponseEntity like(
            @Valid @PathVariable String id,
            HttpServletRequest request) {

        String userId = request.getHeader("userId");

        postService.like(id, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/dislike")
    public ResponseEntity dislike(
            @Valid @PathVariable String id,
            HttpServletRequest request) {

        String userId = request.getHeader("userId");

        postService.dislike(id, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity addComment(
            @Valid @PathVariable String id,
            @Valid @RequestBody AddComment addComment,
            HttpServletRequest request) {

        String authorId = request.getHeader("userId");

        postService.addComment(id, addComment, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/comment/{commentId}")
    public ResponseEntity deleteComment(
            @Valid @PathVariable String id,
            @Valid @PathVariable String commentId) {

        postService.deleteComment(id, commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/comment/{commentId}")
    public ResponseEntity updateComment(
            @Valid @PathVariable String id,
            @Valid @PathVariable String commentId,
            @Valid @RequestBody UpdateComment updateComment) {

        postService.updateComment(id, commentId,updateComment);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
