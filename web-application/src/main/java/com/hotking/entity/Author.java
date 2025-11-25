package com.hotking.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Author {

    String username;
    String email;
}
