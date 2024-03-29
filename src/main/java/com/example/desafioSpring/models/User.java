package com.example.desafioSpring.models;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String name;
    private boolean isSeller;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<User> followersList;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<User> followingList;

    @OneToMany(mappedBy = "user")
    private List<Post> post;

    public void followUser(User userToFollow){
        followingList.add(userToFollow);
        userToFollow.getFollowersList().add(this);
    }

    public void unfollowUser(User userToUnfollow){
        followingList.remove(userToUnfollow);
        userToUnfollow.getFollowersList().remove(this);
    }

    public int qtyFollowers(){
        return followersList.size();
    }

    public User(String name) {
        this.name = name;
        this.followersList = new HashSet<>();
        this.followingList = new HashSet<>();
        if(this.isSeller)
            this.post = new ArrayList<>();
    }
    public User(String name,boolean isSeller) {
        this.name = name;
        this.followersList = new HashSet<>();
        this.followingList = new HashSet<>();
        this.isSeller = isSeller;
        if(this.isSeller)
            this.post = new ArrayList<>();
    }

    public User() {
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getFollowersList() {
        return followersList;
    }

    public void setFollowersList(Set<User> followersList) {
        this.followersList = followersList;
    }

    public Set<User> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(Set<User> followingList) {
        this.followingList = followingList;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }
}
