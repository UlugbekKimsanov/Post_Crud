package com.example.post_crud.controller;

import com.example.post_crud.dto.AuthResponse;
import com.example.post_crud.dto.CreateRequest;
import com.example.post_crud.dto.LoginRequest;
import com.example.post_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody CreateRequest user) {
        return userService.signUp(user);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {
        return userService.signIn(loginRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestParam String refreshToken) {
        return userService.refreshToken(refreshToken);
    }


}
