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
    @DisplayName("Пост с уникальным заголовком должен создаваться всегда")
    void createNewPostTest(){
        Post post = Post.builder()
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
        Post post = Post.builder()
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
    class findPostByTitles{
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
        void findPostByTitleTest(String title){
            Optional<Post> post = PostsDao.getInstance().findByTitle(title);
            assertThat(post).isPresent();
            assertThat(post.get().getTitle()).isIn(titles);
        }

        static Stream<Arguments> getArgumentsForFindPostByTitle(){
            return titles.stream().map(Arguments::of);
        }
    }

    @Nested
    class findPostByAuthorUsername{
        static List<String> authors = List.of("ivan_petrov",
                "anna_smith",
                "max_code",
                "alex_tech"
        );

        @ParameterizedTest
        @MethodSource("getArgumentsForFindPostByAuthor")
        @DisplayName("Поиск постов по имени пользователя автора")
        void findPostByAuthorTest(String author){
            Optional<Post> post = PostsDao.getInstance().findByAuthor(author);
            assertThat(post).isPresent();
            assertThat(post.get().getAuthor().getUsername()).isIn(authors);
        }

        static Stream<Arguments> getArgumentsForFindPostByAuthor(){
            return authors.stream().map(Arguments::of);
        }
    }
}
