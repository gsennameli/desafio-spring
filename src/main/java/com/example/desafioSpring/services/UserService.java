package com.example.desafioSpring.services;

import com.example.desafioSpring.dtos.FollowersDTO;
import com.example.desafioSpring.dtos.FollowingDTO;
import com.example.desafioSpring.dtos.UserDTO;
import com.example.desafioSpring.dtos.UserQtyFollowersDTO;
import com.example.desafioSpring.models.User;
import com.example.desafioSpring.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity followUser(int userId, int userIdToFollow){
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


    public List<UserDTO> findAllUsers(){
        List<User> usersList = userRepository.findAll();
        List<UserDTO> usersDtoList = new ArrayList<>();

        for(User user : usersList){
            usersDtoList.add(new UserDTO(user.getId(),user.getName()));
        }
        return usersDtoList;
    }

    public User findUserById(int userId){
        return userRepository.findById(userId).orElse(null);
    }

    public UserQtyFollowersDTO qtyFollowers(int userId){
        User user = userRepository.findById(userId).orElse(null);
        return new UserQtyFollowersDTO(user);
    }

    public FollowersDTO listFollowers(int userId){
        User user = userRepository.findById(userId).orElse(null);
        return new FollowersDTO(user);
    }

    public User createUser(String userName){
        User user = new User(userName);
        return userRepository.save(user);
    }

    public FollowingDTO listFollowing(int userId){
        User user = userRepository.findById(userId).orElse(null);
        return new FollowingDTO(user);
    }

    public ResponseEntity unfollowUser(int userId,int userToUnfollowId){
        User user = userRepository.findById(userId).orElse(null);
        User userToUnfollow = userRepository.findById(userToUnfollowId).orElse(null);
        if(userToUnfollow == null || userId == userToUnfollowId)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        user.unfollowUser(userToUnfollow);
        userRepository.save(user);
        userRepository.save(userToUnfollow);
        return new ResponseEntity(HttpStatus.OK);
    }




}
