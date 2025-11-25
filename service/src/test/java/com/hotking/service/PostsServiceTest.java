package com.hotking.service;

import com.hotking.dao.PostsDao;
import com.hotking.entity.Post;
import com.hotking.entity.PostToShowAllPosts;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.Random.class)
public class PostsServiceTest {

    @Test
    void getAllPostsTest(){
        List<PostToShowAllPosts> list = PostsService.getInstance().getAllPosts();
        assertThat(list).hasSizeGreaterThanOrEqualTo(6);
    }

    @Test
    void getPostByTitleLevenshtein(){
        List<PostToShowAllPosts> list = PostsService.getInstance().getPostByTitleLevenshtein("Мй превфй поост");
        assertThat(list).hasSizeGreaterThanOrEqualTo(1);
        assertThat(list.get(0).getTitle()).isEqualTo("Мой первый пост");
    }
}
