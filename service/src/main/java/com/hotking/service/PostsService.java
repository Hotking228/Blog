package com.hotking.service;

import com.hotking.dao.AuthorDao;
import com.hotking.dao.PostsDao;
import com.hotking.entity.AuthorDto;
import com.hotking.entity.PostDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostsService {
    private final int MAX_DIST = 10;
    private static final PostsService INSTANCE = new PostsService();

    public static PostsService getInstance(){
        return INSTANCE;
    }

    public List<PostDto> getAllPosts(){
        return PostsDao.getInstance().findAll()
                .stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .build())
                .collect(toList());
    }

    public List<PostDto> getPostByTitleLevenshtein(String title){
        List<PostDto> list = getAllPosts();
        PriorityQueue<PostDto> queue = new PriorityQueue<>(Comparator.comparingInt(post -> {
            String titleFromDb = post.getTitle();
            return calculateDistance(title, titleFromDb);
        }));
        List<PostDto> ans = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            queue.add(list.get(i));
        }
        while(!queue.isEmpty()){
            ans.add(queue.poll());
        }
        return ans;
    }

    private Integer calculateDistance(String title, String titleFromDb){

        int[][]dp = new int[title.length() + 1][titleFromDb.length() + 1];
        for (int j = 0; j < dp.length; j++) {
            dp[j][0] = j;
        }
        for (int j = 0; j < dp[0].length; j++) {
            dp[0][j] = j;
        }
        for (int j = 1; j < dp.length; j++) {
            for (int k = 1; k < dp[j].length; k++) {
                dp[j][k] = Math.min(Math.min(dp[j - 1][k] + 1,dp[j][k - 1] + 1), dp[j - 1][k - 1] + (title.charAt(j - 1) == titleFromDb.charAt(k - 1) ? 0 : 1));
            }
        }

        return dp[dp.length - 1][dp[0].length - 1];
    }

    public boolean createPost(String title, String content, AuthorDto author){

        return PostsDao.getInstance().createNewPost(PostDto.builder()
                .title(title)
                .author(author)
                .content(content)
                .build()
        );
    }

    
    public boolean deletePostByTitle(String title){
        return PostsDao.getInstance().deletePost(title);
    }

    public Optional<PostDto> findPostById(int id) {
        return PostsDao.getInstance().findById(id);
    }

    public void editPost(int id, String title, String content) {
        PostsDao.getInstance().editPost(PostDto.builder()
                .content(content)
                .title(title)
                .id(id)
                .build()
        );
    }

    public boolean tryEditPost(AuthorDto author, int postId) {
        java.util.Optional<PostDto> post = PostsDao.getInstance().findById(postId);
        if(post.isEmpty()) return false;
        Optional<AuthorDto> authorFromDb = AuthorDao.getInstance().findById(post.get().getAuthor().getId());
        return authorFromDb.isPresent() && Objects.equals(authorFromDb.get().getId(), author.getId());
    }

    public List<PostDto> getPostByAuthorId(int id) {
        return PostsDao.getInstance().findByAuthorId(id);
    }
}
