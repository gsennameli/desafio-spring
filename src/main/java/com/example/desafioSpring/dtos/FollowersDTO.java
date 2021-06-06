package com.example.desafioSpring.dtos;

import com.example.desafioSpring.models.User;

import java.util.HashSet;
import java.util.Set;

public class FollowersDTO {
    private int userId;
    private String userName;
    private Set<UserDTO> followers = new HashSet<>();

    public FollowersDTO(User user) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.followers = addFollowersDTO(user.getFollowersList());
    }
    public Set<UserDTO> addFollowersDTO(Set<User> listFollowers){
        for(User user : listFollowers){
            followers.add(new UserDTO(user.getId(), user.getName()));
        }
        return followers;
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

    public Set<UserDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserDTO> followers) {
        this.followers = followers;
    }
}
