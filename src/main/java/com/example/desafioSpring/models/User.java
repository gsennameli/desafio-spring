package com.example.desafioSpring.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name="usuario")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToMany
    private Set<User> followers;
    @ManyToMany
    private Set<User> following;

    public void followUser(User user){
        following.add(user);
        user.getFollowers().add(this);
    }

    public int qtyFollowers(){
        int qtyFollowers = 0;
        for(User user : followers){
            qtyFollowers++;
        }
        return qtyFollowers;
    }

    public User(String name) {
        this.name = name;
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }
}
