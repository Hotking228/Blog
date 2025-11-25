package com.hotking.entity;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class Post {
    String title;
    String content;
    Author author;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
