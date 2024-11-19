package com.example.post_crud.entity;

import org.hibernate.annotations.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID id;

    @CreationTimestamp
    protected LocalDateTime createdDate;

    {
        createdDate = LocalDateTime.now();
    }

}
