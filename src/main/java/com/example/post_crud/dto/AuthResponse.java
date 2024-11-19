package com.example.post_crud.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse {
    private String message;
    private String jwtToken;
    private String refreshToken;


}
