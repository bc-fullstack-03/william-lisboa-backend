package com.sysmap.parrot.service.post.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.sysmap.parrot.dto.post.*;
import com.sysmap.parrot.exception.post.PostNotFoundException;
import com.sysmap.parrot.exception.post.UnableLikeException;
import com.sysmap.parrot.model.embedded.Comment;
import com.sysmap.parrot.model.entity.Post;
import com.sysmap.parrot.repository.PostRepository;
import com.sysmap.parrot.s3.AwsS3Service;
import com.sysmap.parrot.service.post.IPostService;
import com.sysmap.parrot.service.user.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private ModelMapper modelMapper;

    private final AwsS3Service awsS3Service;

    private final UserServiceImpl userService;

    @Override
    public CreatePostResponse publishContent(PublishContentPost publishContentPost, String authorId) {
        var post = new Post();
        post.setContent(publishContentPost.getContent());
        post.setAuthorId(authorId);
        post.setId(UUID.randomUUID().toString());

        postRepository.save(post);
        return modelMapper.map(post, CreatePostResponse.class);
    }

    @Override
    public CreatePostResponse publishPicture(MultipartFile file, String authorId) {
        var post = new Post();
        post.setId(UUID.randomUUID().toString());

        String filepath = awsS3Service.uploadPostPicture(file);
        post.setPictureImage(filepath);
        post.setAuthorId(authorId);

        postRepository.save(post);
        return modelMapper.map(post, CreatePostResponse.class);
    }

    @Override
    public S3Object getPostImage(String url) {
        return awsS3Service.downloadImage(url);
    }


    @Override
    public void updateContent(UpdateContentPost updateContentPost, String postId) {
        var post = getPostById(postId);

        post.setContent(updateContentPost.getContent());
        postRepository.save(post);
    }

    @Override
    public List<GetPosts> getAllPostsByAuthor(String authorId) {
        return postRepository.findAllByAuthorId(authorId)
                .stream()
                .map(post -> modelMapper.map(post,GetPosts.class))
                .toList();
    }

    @Override
    public List<GetPosts> GetAllPostsFollow(String userId) {
        var user = userService.getUserById(userId);
        var following = user.getFollowing();

        var posts = postRepository.findAll();

        var postFollowing = posts.stream()
                .filter(post -> following.contains(post.getAuthorId()));

        return postFollowing
                .map(post -> modelMapper.map(post,GetPosts.class))
                .toList();
    }

    @Override
    public List<GetPosts> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> modelMapper.map(post, GetPosts.class))
                .toList();
    }

    @Override
    public void deletePost(String id) {
        var post = getPostById(id);

        postRepository.deleteById(id);
    }

    @Override
    public void like(String postId, String userId) {
        var post = getPostById(postId);

        var likes = post.getLikes();

        if (!likes.contains(userId)){
            likes.add(userId);
            post.setLikes(likes);
            postRepository.save(post);
        } else {
            throw new UnableLikeException();
        }
    }

    @Override
    public void dislike(String postId, String userId) {
        var post = getPostById(postId);

        var likes = post.getLikes();

        if (likes.contains(userId)){
            likes.remove(userId);
        }else {
            throw new RuntimeException("não foi possil dar dislike no post");
        }

        post.setLikes(likes);
        postRepository.save(post);
    }

    @Override
    public void addComment(String id, AddComment addComment, String author) {
        var post = getPostById(id);
        var comment = modelMapper.map(addComment, Comment.class);
        comment.setUserId(author);
        comment.setId(UUID.randomUUID().toString());

        var comments = post.getComments();
        comments.add(comment);
        post.setComments(comments);
        postRepository.save(post);
    }

    @Override
    public void deleteComment(String id, String commentId) {
        var post = getPostById(id);

        var comments = post.getComments();
        comments.removeIf(comment -> (comment.getId().equals(commentId)) );

        post.setComments(comments);
        postRepository.save(post);
    }

    @Override
    public void updateComment(String id, String commentId, UpdateComment updateComment) {
        var post = getPostById(id);
        var comments = post.getComments();

        for (Comment comment: comments){
            if (comment.getId().equals(commentId));
            comment.setContent(updateComment.getContent());
        }

        post.setComments(comments);
        postRepository.save(post);
    }

    private Post getPostById(String id){
        var post = postRepository.findById(id);

        return post.orElseThrow(() -> new PostNotFoundException());
    }
}
