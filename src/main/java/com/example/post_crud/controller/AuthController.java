package com.example.post_crud.controller;

import com.example.post_crud.config.JwtUtil;
import com.example.post_crud.dto.AuthResponse;
import com.example.post_crud.dto.CreateRequest;
import com.example.post_crud.dto.LoginRequest;
import com.example.post_crud.entity.UserEntity;
import com.example.post_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtTokenUtil;
    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserEntity user = userService.authenticateByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).body(new AuthResponse("Invalid credentials", null, null));
        }

        String jwtToken = jwtTokenUtil.generateAccessToken(user.getId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getId());

        return ResponseEntity.ok(new AuthResponse("Authentication successful", jwtToken, refreshToken));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> register(@RequestBody CreateRequest request) {
        return userService.signUp(request);
    }


}
