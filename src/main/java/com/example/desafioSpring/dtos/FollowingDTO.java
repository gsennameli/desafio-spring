package com.example.desafioSpring.dtos;

import com.example.desafioSpring.models.User;

import java.util.HashSet;
import java.util.Set;

public class FollowingDTO {
    private int userId;
    private String userName;
    private Set<UserDTO> following = new HashSet<>();

    public FollowingDTO(User user) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.following = addFollowingDTO(user.getFollowingList());
    }
    public Set<UserDTO> addFollowingDTO(Set<User> listFollowing){
        for(User user : listFollowing){
            following.add(new UserDTO(user.getId(), user.getName()));
        }
        return following;
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

    public Set<UserDTO> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserDTO> following) {
        this.following = following;
    }
}
