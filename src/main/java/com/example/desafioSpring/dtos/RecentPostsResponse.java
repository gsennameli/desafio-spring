package com.example.desafioSpring.dtos;

import com.example.desafioSpring.models.Post;

import java.util.ArrayList;
import java.util.List;

public class RecentPostsResponse {
    private int userId;
    private List<RecentPostDTO> posts;

    public RecentPostsResponse(int userId, List<Post> postsList) {
        this.userId = userId;
        this.posts = convertPostToPostResponse(postsList);
    }
    public List<RecentPostDTO> convertPostToPostResponse(List<Post> postsList){
        List<RecentPostDTO> list = new ArrayList<>();
        for(Post post : postsList){
            list.add(new RecentPostDTO(post,post.getDetail()));
        }
        return list;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<RecentPostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<RecentPostDTO> posts) {
        this.posts = posts;
    }
}
