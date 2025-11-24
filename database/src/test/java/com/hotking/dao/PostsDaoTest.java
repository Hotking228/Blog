package com.hotking.dao;

import com.hotking.entity.Post;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.Random.class)
public class PostsDaoTest {

    PostsDao postsDao;

    @BeforeEach
    void prepare(){
        postsDao = PostsDao.getInstance();
    }

    @Test
    @DisplayName("Поиск всех (хотя бы дефолтных) статьей")
    void shouldFindAtLeastDefaultPostsTest(){
        List<Post> list = PostsDao.getInstance().findAll();
        assertThat(list).hasSizeGreaterThanOrEqualTo(6);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForShouldFindPostById")
    @DisplayName("Поиск статьей по номеру")
    void shouldFindPostByIdTest(int id){
        Optional<Post> maybePost = PostsDao.getInstance().findById(id);
        assertThat(maybePost).isPresent();
        assertThat(maybePost.get().getId()).isEqualTo(id);
    }

    static Stream<Arguments> getArgumentsForShouldFindPostById(){
        return Stream.of(Arguments.of(7),
                         Arguments.of(8),
                         Arguments.of(9),
                         Arguments.of(10),
                         Arguments.of(11),
                         Arguments.of(12)
        );
    }

    @RepeatedTest(3)
    @DisplayName("Пост должен создаваться всегда")
    void createNewPostTest(){
        Post post = Post.builder()
                .title("title")
                .content("content")
                .author(AuthorDao.getInstance().findById(1).get())
                .build();
        boolean createResult = PostsDao.getInstance().createNewPost(post);
        assertThat(createResult).isTrue();
    }
}
