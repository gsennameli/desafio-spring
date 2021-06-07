package com.example.desafioSpring.services;

import com.example.desafioSpring.dtos.*;
import com.example.desafioSpring.models.Post;
import com.example.desafioSpring.models.User;
import com.example.desafioSpring.repositories.PostRepository;
import com.example.desafioSpring.repositories.ProductRepository;
import com.example.desafioSpring.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

        if(productRepository.findById(postRequest.getDetail().getId()).isPresent())
            return new ResponseEntity(new ErrorHandlingDTO("This product Id already exists"),HttpStatus.BAD_REQUEST);

        User user = userRepository.findById(postRequest.getUser_id()).orElse(null);
        if(user == null)
            return new ResponseEntity(new ErrorHandlingDTO("User id not found"),HttpStatus.NOT_FOUND);
        else if (user.isSeller() == false)
            return new ResponseEntity(new ErrorHandlingDTO("You can not assign a post to a not seller user"),HttpStatus.BAD_REQUEST);

        Post post = new Post(postRequest.getId(),postRequest.getDate(),postRequest.getCategory(),postRequest.getPrice(),postRequest.getDetail(),user);
        postRepository.save(post);

        PostResponse postResponse = new PostResponse(post);
        return new ResponseEntity(postResponse,HttpStatus.OK);
    }

    public ResponseEntity listPosts(int userId,String order){
        User user = userRepository.findById(userId).orElse(null);
        List<Post> postsList = new ArrayList<>();

        for(User elem : user.getFollowingList()){
            postsList.addAll(elem.getPost());
        }
        if(order == null)
            return new ResponseEntity(new RecentPostsResponse(userId,postsList),HttpStatus.OK);
        if(order.equals("date_asc"))
            postsList.sort(Comparator.comparing(Post::getDate));
        else if (order.equals("date_desc"))
            postsList.sort(Comparator.comparing(Post::getDate).reversed());
        else
            return new ResponseEntity(new ErrorHandlingDTO("Invalid Param"),HttpStatus.BAD_REQUEST);

        RecentPostsResponse recentPostsResponse = new RecentPostsResponse(userId,postsList);
        return new ResponseEntity(recentPostsResponse,HttpStatus.OK);
    }

    public ResponseEntity createPromoPost(PromoPostRequest promoPostRequest){
        if(productRepository.findById(promoPostRequest.getDetail().getId()).isPresent())
            return new ResponseEntity(new ErrorHandlingDTO("This product Id already exists"),HttpStatus.BAD_REQUEST);

        User user = userRepository.findById(promoPostRequest.getUser_id()).orElse(null);
        if(user == null)
            return new ResponseEntity(new ErrorHandlingDTO("User id not found"),HttpStatus.NOT_FOUND);
        else if (user.isSeller() == false)
            return new ResponseEntity(new ErrorHandlingDTO("You can not assign a post to a not seller user"),HttpStatus.BAD_REQUEST);
        else if (promoPostRequest.isHasPromo() == false)
            return new ResponseEntity(new ErrorHandlingDTO("This product do not have promo"),HttpStatus.BAD_REQUEST);

        Post post = new Post(promoPostRequest.getId(),promoPostRequest.getDate(),promoPostRequest.getCategory(),promoPostRequest.getPrice(),promoPostRequest.getDetail(),user);
        post.setHasPromo(promoPostRequest.isHasPromo());
        post.setDiscount(promoPostRequest.getDiscount());
        postRepository.save(post);

        PostResponse postResponse = new PostResponse(post);
        return new ResponseEntity(postResponse,HttpStatus.OK);
    }

    public ResponseEntity getQuantityOfPromoPosts(int userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null)
            return new ResponseEntity(new ErrorHandlingDTO("User id not found"),HttpStatus.NOT_FOUND);
        else if (user.isSeller() == false)
            return new ResponseEntity(new ErrorHandlingDTO("This user is not a seller"),HttpStatus.BAD_REQUEST);

        int countPromoPosts = 0;
        for(Post post : user.getPost()){
            if(post.isHasPromo())
                countPromoPosts++;
        }

        return new ResponseEntity(new QuantityPromoPostsResponse(user,countPromoPosts),HttpStatus.OK);
    }
}
