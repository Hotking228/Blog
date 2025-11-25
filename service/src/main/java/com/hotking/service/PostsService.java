package com.hotking.service;

import com.hotking.dao.PostsDao;
import com.hotking.entity.Post;
import com.hotking.entity.PostToShowAllPosts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostsService {
    private final int MAX_DIST = 10;
    private static final PostsService INSTANCE = new PostsService();

    public static PostsService getInstance(){
        return INSTANCE;
    }

    public List<PostToShowAllPosts> getAllPosts(){
        return PostsDao.getInstance().findAll()
                .stream()
                .map(post -> PostToShowAllPosts.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .build())
                .collect(toList());
    }

    public List<PostToShowAllPosts> getPostByTitleLevenshtein(String title){
        List<PostToShowAllPosts> list = getAllPosts();
        List<PostToShowAllPosts> ans = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String titleFromDb = list.get(i).getTitle();
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

            if(dp[dp.length - 1][dp[0].length - 1] <= MAX_DIST || title.isEmpty()){
                ans.add(list.get(i));
            }
        }

        return ans;
    }

    public boolean createPost(Post post){
        return PostsDao.getInstance().createNewPost(post);
    }

    public Optional<Post> findPostByAuthorId(String username){
        return PostsDao.getInstance().findByAuthorUsername(username);
    }
    
    public boolean deletePostByTitle(String title){
        return PostsDao.getInstance().deletePost(title);
    }
}
