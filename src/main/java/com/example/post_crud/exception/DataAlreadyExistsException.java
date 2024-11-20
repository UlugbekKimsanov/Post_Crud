package com.example.post_crud.exception;

public class DataAlreadyExistsException extends RuntimeException {
    public DataAlreadyExistsException(String msg) {
        super(msg);
    }
}