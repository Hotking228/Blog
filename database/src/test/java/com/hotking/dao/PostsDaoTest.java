package com.hotking.dao;

import com.hotking.entity.PostDto;
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
        List<PostDto> list = PostsDao.getInstance().findAll();

        assertThat(list).hasSizeGreaterThanOrEqualTo(6);
    }

    @RepeatedTest(3)
    @DisplayName("Пост с уникальным заголовком должен создаваться всегда")
    void createNewPostTest(){
        PostDto post = PostDto.builder()
                .title("title")
                .content("content")
                .author(AuthorDao.getInstance().findById(1).get())
                .build();
        boolean firstCreate = PostsDao.getInstance().createNewPost(post);
        boolean secondCreate = PostsDao.getInstance().createNewPost(post);
        PostsDao.getInstance().deletePost(post.getTitle());
        assertThat(firstCreate).isTrue();
        assertThat(secondCreate).isFalse();
    }

    @RepeatedTest(3)
    @DisplayName("Пост должен удаляться по заголовку(заголовки уникальны)")
    void deletePostTest(){
        PostDto post = PostDto.builder()
                .title("title")
                .content("content")
                .author(AuthorDao.getInstance().findById(1).get())
                .build();
        PostsDao.getInstance().createNewPost(post);
        boolean firstDelete = PostsDao.getInstance().deletePost(post.getTitle());
        boolean secondDelete = PostsDao.getInstance().deletePost(post.getTitle());
        assertThat(firstDelete).isTrue();
        assertThat(secondDelete).isFalse();
    }

    @Nested
    class findPostByTitles {
        static List<String> titles = List.of("Мой первый пост",
                "Java для начинающих",
                "Рефлексия в Java",
                "Maven и управление зависимостями",
                "JDBC и работа с БД",
                "Тестирование с JUnit 5"
        );

        @ParameterizedTest
        @MethodSource("getArgumentsForFindPostByTitle")
        @DisplayName("Поиск постов по заголовку")
        void findPostByTitleTest(String title) {
            Optional<PostDto> post = PostsDao.getInstance().findByTitle(title);
            assertThat(post).isPresent();
            assertThat(post.get().getTitle()).isIn(titles);
        }

        static Stream<Arguments> getArgumentsForFindPostByTitle() {
            return titles.stream().map(Arguments::of);
        }
    }

}
