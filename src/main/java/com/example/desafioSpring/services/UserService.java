package com.example.desafioSpring.services;

import com.example.desafioSpring.dtos.*;
import com.example.desafioSpring.models.User;
import com.example.desafioSpring.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    public ResponseEntity listFollowers(int userId,String order){
        User user = userRepository.findById(userId).orElse(null);
        FollowersDTO followersDTO = new FollowersDTO(user);
        if(order == null)
            return new ResponseEntity(followersDTO,HttpStatus.OK);
        if(order.equals("name_asc"))
            followersDTO.getFollowers().sort(Comparator.comparing(UserDTO::getUserName));
        else if (order.equals("name_desc"))
            followersDTO.getFollowers().sort(Comparator.comparing(UserDTO::getUserName).reversed());
        else
            return new ResponseEntity(new ErrorHandlingDTO("Invalid Param"),HttpStatus.BAD_REQUEST);

        return new ResponseEntity(followersDTO,HttpStatus.OK);
    }

    public User createUser(String userName){
        User user = new User(userName);
        return userRepository.save(user);
    }

    public ResponseEntity listFollowing(int userId,String order){
        User user = userRepository.findById(userId).orElse(null);
        FollowingDTO followingDTO = new FollowingDTO(user);
        if(order == null)
            return new ResponseEntity(followingDTO,HttpStatus.OK);
        if(order.equals("name_asc"))
            followingDTO.getFollowing().sort(Comparator.comparing(UserDTO::getUserName));
        else if (order.equals("name_desc"))
            followingDTO.getFollowing().sort(Comparator.comparing(UserDTO::getUserName).reversed());
        else
            return new ResponseEntity(new ErrorHandlingDTO("Invalid Param"),HttpStatus.BAD_REQUEST);

        return new ResponseEntity(followingDTO,HttpStatus.OK);
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
