package com.example.post_crud.service;

import com.example.post_crud.dto.PostDto;
import com.example.post_crud.dto.PostResponse;
import com.example.post_crud.entity.Post;
import com.example.post_crud.entity.UserEntity;
import com.example.post_crud.exception.DataNotFoundException;
import com.example.post_crud.repository.PostRepository;
import com.example.post_crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ResponseEntity<String> create(String text, UUID uuid) {
        UserEntity user = userRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException("Foydalanuvchi topilmadi!"));
        postRepository.save(new Post(user,text));
        return ResponseEntity.ok("Post yaratildi!");
    }

    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<Post> allPosts = postRepository.findAll();
        if (!allPosts.isEmpty()) {
            List<PostDto> postDtoList = new ArrayList<>();
            allPosts.forEach(post -> {
                postDtoList.add(new PostDto(post.getPost(), post.getOwner().getName(),post.getCreatedDate()));
            });
            return ResponseEntity.ok(postDtoList);
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<List<PostResponse>> getMyPosts(UUID userId) {
        return ResponseEntity.ok(postRepository.findPostsByOwnerId(userId));
    }

    public ResponseEntity<String> delete(UUID postId, UUID userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Post topilmadi!"));
        if(post.getOwner().getId().equals(userId)) {
            postRepository.delete(post);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Post o'chirildi!");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Siz bu postni o'chira olmaysiz!");
    }

    public ResponseEntity<String> update(String text,UUID postId, UUID userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Post topilmadi!"));
        if(post.getOwner().getId().equals(userId)) {
            post.setPost(text);
            postRepository.save(post);
            return ResponseEntity.ok().body("Post yangilandi!");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Siz ushbu postni yangilay olmaysiz!");
    }
}
