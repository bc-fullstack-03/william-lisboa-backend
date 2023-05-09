package com.sysmap.parrot.repository;

import com.sysmap.parrot.model.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findAllByAuthorId(String id);

}
