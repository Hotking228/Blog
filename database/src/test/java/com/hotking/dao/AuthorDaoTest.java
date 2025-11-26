package com.hotking.dao;

import com.hotking.entity.AuthorDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.Random.class)
public class AuthorDaoTest {

    AuthorDao userDao;

    @BeforeEach
    void prepare(){
        userDao = AuthorDao.getInstance();
    }

    @Test
    @DisplayName("Поиск всех авторов(по крайней мере дефолтных)")
    void findAtLeastDefaultAuthorsTest(){
        List<AuthorDto> authors = userDao.findAll();
        System.out.println(authors);
        assertThat(authors).hasSizeGreaterThanOrEqualTo(4);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForFindAuthor")
    @DisplayName("Поиск авторов по id")
    void findAuthorByIdTest(Integer id){
        Optional<AuthorDto> maybeAuthor = AuthorDao.getInstance().findById(id);
        assertThat(maybeAuthor).isPresent();
        assertThat(maybeAuthor.get().getId()).isEqualTo(id);
    }

    static Stream<Arguments> getArgumentsForFindAuthor(){
        return Stream.of(Arguments.of(1),
                         Arguments.of(2),
                         Arguments.of(3),
                         Arguments.of(4)
                );
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForFindAuthorByUsernameOrEmailAndPassword")
    @DisplayName("Поиск авторов по имени пользователя или почте и паролю - для логина")
    void findAuthorByUsernameOrEmailAndPasswordTest(String username, String email, String password){
        Optional<AuthorDto> author = AuthorDao.getInstance().findAuthorByUsernameOrEmailAndPassword(username, email, password);
        if((username.equals("dummy") && email.equals("dummy")) || password.equals("dummy")){
            assertThat(author).isEmpty();
        }else{
            assertThat(author).isPresent();
        }
    }

    static Stream<Arguments> getArgumentsForFindAuthorByUsernameOrEmailAndPassword(){
        return Stream.of(Arguments.of("ivan_petrov", "ivan@example.com", "password123"),
                         Arguments.of("max_code", "dummy", "max_secure"),
                         Arguments.of("dummy", "anna@example.com", "anna_pass"),
                         Arguments.of("dummy", "dummy", "dummy"),
                         Arguments.of("alex_tech", "alex@tech.org", "dummy"),
                         Arguments.of("vano", "dummy", "123")

                );
    }

    @RepeatedTest(3)
    @DisplayName("Автор с одинаковой почтой/именем пользователя должен создаться 1 раз")
    void createNewAuthorTest(){
        AuthorDto dummy = AuthorDto.builder()
                .username("username")
                .email("email")
                .password("password")
                .build();
        boolean firstResult = AuthorDao.getInstance().createNewAuthor(dummy);
        boolean secondResult = AuthorDao.getInstance().createNewAuthor(dummy);
        assertThat(firstResult).isTrue();
        assertThat(secondResult).isFalse();
        AuthorDao.getInstance().removeAuthorByUsername(dummy.getUsername());
    }

    @RepeatedTest(3)
    @DisplayName("Автор с должен удаляться 1 раз по имени пользователя")
    void deleteAuthorTest(){
        AuthorDto dummy = AuthorDto.builder()
                .username("username")
                .email("email")
                .password("password")
                .build();
        AuthorDao.getInstance().createNewAuthor(dummy);
        boolean firstResult = AuthorDao.getInstance().removeAuthorByUsername(dummy.getUsername());
        boolean secondResult = AuthorDao.getInstance().removeAuthorByUsername(dummy.getUsername());
        assertThat(firstResult).isTrue();
        assertThat(secondResult).isFalse();
    }
}