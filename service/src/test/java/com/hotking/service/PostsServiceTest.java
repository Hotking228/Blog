package com.hotking.service;

import com.hotking.entity.PostDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.Random.class)
public class PostsServiceTest {

    @Test
    void getAllPostsTest(){
        List<PostDto> list = PostsService.getInstance().getAllPosts();
        assertThat(list).hasSizeGreaterThanOrEqualTo(6);
    }

    @Test
    void getPostByTitleLevenshtein(){
        List<PostDto> list = PostsService.getInstance().getPostByTitleLevenshtein("Мй превфй поост");
        assertThat(list).hasSizeGreaterThanOrEqualTo(1);
        assertThat(list.get(0).getTitle()).isEqualTo("Мой первый пост");
    }
}
