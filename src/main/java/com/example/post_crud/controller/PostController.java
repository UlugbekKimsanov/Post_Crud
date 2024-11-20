package com.example.post_crud.controller;

import com.example.post_crud.dto.PostDto;
import com.example.post_crud.dto.PostResponse;
import com.example.post_crud.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestParam String text, Principal principal){
        System.out.println("principal.getName() = " + principal.getName());
        return postService.create(text, UUID.fromString(principal.getName()));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePost(@RequestParam String text, @RequestParam UUID postId, Principal principal){
        return postService.update(text, postId, UUID.fromString(principal.getName()));
    }

    @PutMapping("/delete")
    public ResponseEntity<String> deletePost(@RequestParam UUID postId, Principal principal){
        return postService.delete(postId, UUID.fromString(principal.getName()));
    }
    @GetMapping("/getAllPosts")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/getMyPosts")
    public ResponseEntity<List<PostResponse>> getMyPosts(Principal principal){
        return postService.getMyPosts(UUID.fromString(principal.getName()));
    }

}
