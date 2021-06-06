package com.example.desafioSpring.dtos;

import com.example.desafioSpring.models.User;

public class UserQtyFollowersDTO {
    private int userId;
    private String userName;
    private int followers_count;

    public UserQtyFollowersDTO(User user) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.followers_count = user.qtyFollowers();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }
}
