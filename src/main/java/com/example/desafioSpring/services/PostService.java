package com.example.desafioSpring.services;

import com.example.desafioSpring.dtos.ErrorHandlingDTO;
import com.example.desafioSpring.dtos.PostRequest;
import com.example.desafioSpring.dtos.PostResponse;
import com.example.desafioSpring.models.Post;
import com.example.desafioSpring.models.User;
import com.example.desafioSpring.repositories.PostRepository;
import com.example.desafioSpring.repositories.ProductRepository;
import com.example.desafioSpring.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    PostService(PostRepository postRepository,ProductRepository productRepository,UserRepository userRepository){
        this.postRepository = postRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity createPost(PostRequest postRequest){
        //TODO Post nao esta atrelando a um usuario
        if(productRepository.findById(postRequest.getDetail().getId()).isPresent())
            return new ResponseEntity(new ErrorHandlingDTO("This product Id already exists"),HttpStatus.BAD_REQUEST);
        User user = userRepository.findById(postRequest.getUser_id()).orElse(null);
        if(user == null)
            return new ResponseEntity(new ErrorHandlingDTO("User id not found"),HttpStatus.NOT_FOUND);
        Post post = new Post(postRequest.getId(),postRequest.getDate(),postRequest.getCategory(),postRequest.getPrice(),postRequest.getDetail(),user);
        postRepository.save(post);
        PostResponse postResponse = new PostResponse(post);
        return new ResponseEntity(postResponse,HttpStatus.OK);
    }
}
