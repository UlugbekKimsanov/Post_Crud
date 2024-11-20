package com.example.post_crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private UUID id;
    private String post;
    private LocalDateTime createdDate;
}
