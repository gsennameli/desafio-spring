package com.example.desafioSpring.dtos;

import com.example.desafioSpring.models.User;

public class QuantityPromoPostsResponse {
    private int userId;
    private String userName;
    private int promoproducts_count;

    public QuantityPromoPostsResponse(User user, int promoproducts_count) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.promoproducts_count = promoproducts_count;
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

    public int getPromoproducts_count() {
        return promoproducts_count;
    }

    public void setPromoproducts_count(int promoproducts_count) {
        this.promoproducts_count = promoproducts_count;
    }
}
