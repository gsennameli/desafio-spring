package com.example.desafioSpring.controllers;

import com.example.desafioSpring.dtos.PostRequest;
import com.example.desafioSpring.dtos.PromoPostRequest;
import com.example.desafioSpring.models.Post;
import com.example.desafioSpring.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostController {
    private final PostService postService;

    PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/newpost")
    public ResponseEntity createPost(@RequestBody PostRequest post){
        return postService.createPost(post);
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity listPosts(@PathVariable int userId,@RequestParam(name = "order", required = false) String order){
        return postService.listPosts(userId,order);
    }
    @PostMapping("/newpromopost")
    public ResponseEntity createPromoPost(@RequestBody PromoPostRequest promoPostRequest){
        return postService.createPromoPost(promoPostRequest);
    }
    @GetMapping("/{userId}/countPromo")
    public ResponseEntity getCountPromoPosts(@PathVariable int userId){
        return postService.getQuantityOfPromoPosts(userId);
    }
    @GetMapping("/{userId}/list")
    public ResponseEntity getAllPostsOfSeller(@PathVariable int userId, @RequestParam(name = "order", required = false) String order){
        return postService.getPromoPostsOfSeller(userId,order);
    }

}
