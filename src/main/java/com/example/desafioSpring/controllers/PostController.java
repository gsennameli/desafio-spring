package com.example.desafioSpring.controllers;

import com.example.desafioSpring.dtos.PostRequest;
import com.example.desafioSpring.models.Post;
import com.example.desafioSpring.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class PostController {
    private final PostService postService;

    PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/newpost")
    public ResponseEntity newPost(@RequestBody PostRequest post){
        return postService.createPost(post);
    }
}
