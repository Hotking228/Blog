package com.hotking.dao;

import com.hotking.entity.Author;
import com.hotking.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorDao {

    private static final AuthorDao INSTANCE = new AuthorDao();
    private final String FIND_ALL_AUTHORS_SQL = """
            SELECT 
                id,
                username,
                email,
                password,
                created_at
            FROM authors
            """;
    private final String FIND_AUTHOR_BY_ID = """
            SELECT 
                id,
                username,
                email,
                password,
                created_at
            FROM authors
            WHERE id = ?
            """;
    private final String FIND_AUTHOR_BY_USERNAME_OR_EMAIL_AND_PASSWORD = """
            SELECT 
                id,
                username,
                email,
                password,
                created_at
            FROM authors
            WHERE (username = ? OR email = ?) AND password = ?
            """;
    private static final String ADD_NEW_AUTHOR_SQL = """
            INSERT INTO authors (username, email, password)
            VALUES 
                    (?, ?, ?)
            """;
    private static final String DELETE_AUTHOR_BY_USERNAME = """
            DELETE FROM authors
            WHERE username = ?
            """;

    public static AuthorDao getInstance(){
        return INSTANCE;
    }

    public List<Author> findAll() {
        var connection = ConnectionManager.get();
        try(var statement = connection.prepareStatement(FIND_ALL_AUTHORS_SQL)){
            List<Author> list = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while(resultSet.next()){
                list.add(build(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.returnConnection(connection);
        }
    }

    private Author build(ResultSet resultSet) {
        try {
            return Author.builder()
                    .id(resultSet.getInt("id"))
                    .username(resultSet.getString("username"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<Author> findById(int id) {
        var connection = ConnectionManager.get();
        try(var statement = connection.prepareStatement(FIND_AUTHOR_BY_ID)){
            statement.setObject(1, id);
            var resultSet = statement.executeQuery();
            if(resultSet.next()){
                return Optional.of(build(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.returnConnection(connection);
        }
    }

    public Optional<Author> findAuthorByUsernameOrEmailAndPassword(String username, String email, String password) {
        var connection = ConnectionManager.get();
        try(var statement = connection.prepareStatement(FIND_AUTHOR_BY_USERNAME_OR_EMAIL_AND_PASSWORD)){
            statement.setObject(1, username);
            statement.setObject(2, email);
            statement.setObject(3, password);
            var resultSet = statement.executeQuery();
            if(resultSet.next()){
                return Optional.of(build(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.returnConnection(connection);
        }
    }

    public boolean createNewAuthor(Author dummy) {
        var connection = ConnectionManager.get();
        try(var statement = connection.prepareStatement(ADD_NEW_AUTHOR_SQL)){
            statement.setObject(1, dummy.getUsername());
            statement.setObject(2, dummy.getEmail());
            statement.setObject(3, dummy.getPassword());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            ConnectionManager.returnConnection(connection);
        }
    }

    public boolean removeAuthorByUsername(String username) {
        var connection = ConnectionManager.get();
        try(var statement = connection.prepareStatement(DELETE_AUTHOR_BY_USERNAME, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, username);
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            return generatedKeys.next();
        } catch (SQLException e) {
            return false;
        } finally {
            ConnectionManager.returnConnection(connection);
        }
    }
}
