package com.example.desafioSpring.services;

import com.example.desafioSpring.dtos.FollowersDTO;
import com.example.desafioSpring.dtos.UserQtyFollowersDTO;
import com.example.desafioSpring.models.User;
import com.example.desafioSpring.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity followUser(Long userId, Long userIdToFollow){
        User userToFollow = userRepository.findById(userIdToFollow).orElse(null);
        if(userToFollow == null || userId == userIdToFollow)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return userRepository.findById(userId)
                .map(user -> {
                    user.followUser(userToFollow);
                    userRepository.save(user);
                    userRepository.save(userToFollow);
                    return new ResponseEntity(HttpStatus.OK);
                }).orElse(new ResponseEntity(HttpStatus.BAD_REQUEST));
    }

    public Iterable<User> findAllUsers(){
        return userRepository.findAll();
    }

    public UserQtyFollowersDTO qtyFollowers(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        return new UserQtyFollowersDTO(user);
    }

    public FollowersDTO listFollowers(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        return new FollowersDTO(user);
    }

    public User createUser(String userName){
        User user = new User(userName);
        return userRepository.save(user);
    }
}
