package com.example.post_crud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseEntity{
    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "owner",referencedColumnName = "id")
    private UserEntity owner;
    private String post;

}
