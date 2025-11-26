package com.hotking.entity;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class AuthorDto {

    Integer id;
    String username;
    String email;
    String password;
    LocalDateTime createdAt;
}
