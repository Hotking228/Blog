package com.hotking.entity;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PostToShowAllPosts {
    Integer id;
    String title;
}
