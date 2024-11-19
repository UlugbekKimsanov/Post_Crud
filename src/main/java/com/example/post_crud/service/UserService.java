package com.example.post_crud.service;

import com.example.post_crud.dto.CreateRequest;
import com.example.post_crud.dto.LoginRequest;
import com.example.post_crud.entity.UserEntity;
import com.example.post_crud.repository.PostRepository;
import com.example.post_crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;


    public UserEntity authenticateByEmailAndPassword(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        System.out.println(user);
        if (!(user == null) && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public ResponseEntity<String> signUp(CreateRequest request) {
        UserEntity byEmail = userRepository.findByEmail(request.getEmail());
        if (byEmail == null) {
            UserEntity user = new UserEntity();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setName(request.getName());
            user.setSurname(request.getSurname());
            userRepository.save(user);
            return ResponseEntity.ok("User created");
        }
        return ResponseEntity.badRequest().body("User already registered");
    }
}
