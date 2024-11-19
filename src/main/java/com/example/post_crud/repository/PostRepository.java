package com.example.post_crud.repository;

import com.example.post_crud.dto.PostDto;
import com.example.post_crud.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query("SELECT p FROM Post p WHERE p.owner.id = :ownerId")
    List<Post> findPostsByOwnerId(UUID ownerId);
}
