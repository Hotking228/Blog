package com.hotking.entity;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class PostDto {
    Integer id;
    String title;
    String content;
    AuthorDto author;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
