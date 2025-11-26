package com.hotking.service;

import com.hotking.dao.AuthorDao;
import com.hotking.entity.AuthorDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorService {

    private static final AuthorService INSTANCE = new AuthorService();

    public static AuthorService getInstance(){
        return INSTANCE;
    }

    public List<AuthorDto> findAllAuthor(){
        return AuthorDao.getInstance().findAll();
    }

    public boolean createNewAuthor(String username, String email, String password){
        return AuthorDao.getInstance().createNewAuthor(AuthorDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .build()
        );
    }

    public Optional<AuthorDto> findAuthorById(int id){
        return AuthorDao.getInstance().findById(id);
    }

    public Optional<AuthorDto> findByUsernameOrEmailAndPassword(String username, String email, String password){
        return AuthorDao.getInstance().findAuthorByUsernameOrEmailAndPassword(username, email, password);
    }

    public boolean removeAuthorByUsername(String username){
        return AuthorDao.getInstance().removeAuthorByUsername(username);
    }
}
