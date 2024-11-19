package com.example.post_crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequest {
    private String  name;
    private String surname;
    private String email;
    private String password;
}
