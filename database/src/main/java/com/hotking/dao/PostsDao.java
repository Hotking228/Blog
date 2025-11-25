package com.hotking.dao;

import com.hotking.entity.Post;
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
public class PostsDao {

    private static final PostsDao INSTANCE = new PostsDao();
    private static final String FIND_ALL_POSTS_SQL = """
            SELECT 
                    id,
                    title,
                    content,
                    author_id,
                    created_at,
                    updated_at
            FROM posts
            """;
    private static final String FIND_POST_BY_AUTHOR_SQL = """
            SELECT 
                    posts.id,
                    posts.title,
                    posts.content,
                    posts.author_id,
                    posts.created_at,
                    posts.updated_at
            FROM posts
            JOIN authors ON author_id = authors.id
            WHERE username = ?
            """;
    private static final String CREATE_NEW_POST_SQL = """
            INSERT INTO posts (title, content, author_id)
            VALUES (?, ?, ?)
            """;
    private static final String DELETE_POST_BY_TITLE_SQL = """
            DELETE FROM posts
            WHERE
                title = ?
            """;
    private static final String FIND_POST_BY_TITLE_SQL = """
            SELECT 
                    id,
                    title,
                    content,
                    author_id,
                    created_at,
                    updated_at
            FROM posts
            WHERE title = ?
            """;

    public static PostsDao getInstance(){
        return INSTANCE;
    }

    public List<Post> findAll() {
        var connection = ConnectionManager.get();
        try(var statement = connection.prepareStatement(FIND_ALL_POSTS_SQL)){
            var resultSet = statement.executeQuery();
            List<Post> list = new ArrayList<>();
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

    public Optional<Post> findByTitle(String title){
        var connection = ConnectionManager.get();
        try(var statement = connection.prepareStatement(FIND_POST_BY_TITLE_SQL)){
            statement.setObject(1, title);
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

    public Optional<Post> findByAuthorUsername(String author) {
        var connection = ConnectionManager.get();
        try(var statement = connection.prepareStatement(FIND_POST_BY_AUTHOR_SQL)){
            statement.setObject(1, author);
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

    public boolean createNewPost(Post post) {
        var connection = ConnectionManager.get();
        try(var statement = connection.prepareStatement(CREATE_NEW_POST_SQL)){
            statement.setObject(1, post.getTitle());
            statement.setObject(2, post.getContent());
            statement.setObject(3, post.getAuthor().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            ConnectionManager.returnConnection(connection);
        }
    }

    public boolean deletePost(String title) {
        var connection = ConnectionManager.get();
        try(var statement = connection.prepareStatement(DELETE_POST_BY_TITLE_SQL, Statement.RETURN_GENERATED_KEYS)){
            statement.setObject(1, title);
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            return generatedKeys.next();
        } catch (SQLException e) {
            return false;
        } finally {
            ConnectionManager.returnConnection(connection);
        }
    }

    private Post build(ResultSet resultSet) {
        try {
            return Post.builder()
                    .id(resultSet.getInt("id"))
                    .title(resultSet.getString("title"))
                    .content(resultSet.getString("content"))
                    .author(AuthorDao.getInstance().findById(resultSet.getInt("author_id")).get())
                    .cratedAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                    .updatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
