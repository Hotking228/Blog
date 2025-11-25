package com.hotking.service;

import com.hotking.dao.AuthorDao;
import com.hotking.entity.Author;
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

    public List<Author> findAllAuthor(){
        return AuthorDao.getInstance().findAll();
    }

    public boolean createNewAuthor(Author author){
        return AuthorDao.getInstance().createNewAuthor(author);
    }

    public Optional<Author> findAuthorById(int id){
        return AuthorDao.getInstance().findById(id);
    }

    public Optional<Author> findByUsernameOrEmailAndPassword(String username, String email,String password){
        return AuthorDao.getInstance().findAuthorByUsernameOrEmailAndPassword(username, email, password);
    }

    public boolean removeAuthorByUsername(String username){
        return AuthorDao.getInstance().removeAuthorByUsername(username);
    }
}
