package com.example.desafioSpring.dtos;

import com.example.desafioSpring.models.Post;
import com.example.desafioSpring.models.Product;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

public class PostResponse {
    private int id;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date date;

    private int category;
    private double price;
    private Product detail;
    private UserDTO user;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.date = post.getDate();
        this.category = post.getCategory();
        this.price = post.getCategory();
        this.detail = post.getDetail();
        this.user = new UserDTO(post.getUser().getId(),post.getUser().getName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getDetail() {
        return detail;
    }

    public void setDetail(Product detail) {
        this.detail = detail;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
