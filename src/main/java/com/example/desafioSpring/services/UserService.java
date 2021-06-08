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
        User user = userRepository.findById(userId).orElse(null);

        if(userToFollow == null || userId == userIdToFollow || user == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        if(userToFollow.isSeller() == false)
            return new ResponseEntity(new ErrorHandlingDTO("You can only follow sellers"),HttpStatus.BAD_REQUEST);
        for(User elem : userRepository.findById(userId).get().getFollowingList()){
            if (elem.getId() == userIdToFollow)
                return new ResponseEntity(new ErrorHandlingDTO("You already follow this user"),HttpStatus.BAD_REQUEST);
        }

        user.followUser(userToFollow);
        userRepository.save(user);
        userRepository.save(userToFollow);
        return new ResponseEntity(HttpStatus.OK);
    }


    public List<UserDTO> findAllUsers(){
        List<User> usersList = userRepository.findAll();
        List<UserDTO> usersDtoList = new ArrayList<>();

        for(User user : usersList){
            usersDtoList.add(new UserDTO(user.getId(),user.getName()));
        }
        return usersDtoList;
    }

    public ResponseEntity findUserById(int userId){
        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            return new ResponseEntity(new ErrorHandlingDTO("User id not found"),HttpStatus.BAD_REQUEST);

        UserDTO userDTO = new UserDTO(user.getId(),user.getName());
        return new ResponseEntity(userDTO,HttpStatus.OK);
    }

    public ResponseEntity qtyFollowers(int userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null)
            return new ResponseEntity(new ErrorHandlingDTO("User id not found"),HttpStatus.BAD_REQUEST);
        if(user.isSeller() == false)
            return new ResponseEntity(new ErrorHandlingDTO("Common users do not have followers"),HttpStatus.BAD_REQUEST);

        return new ResponseEntity(new UserQtyFollowersDTO(user),HttpStatus.OK);
    }

    public ResponseEntity listFollowers(int userId,String order){
        User user = userRepository.findById(userId).orElse(null);
        FollowersDTO followersDTO = new FollowersDTO(user);
        if(user.isSeller() == false)
            return new ResponseEntity(new ErrorHandlingDTO("Common users do not have followers"),HttpStatus.BAD_REQUEST);
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

    public User createUser(CreateUserRequest createUserRequest){
        User user = new User(createUserRequest.getName(), createUserRequest.isSeller());
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
        boolean foundUserToUnfollowInFollowingList = false;

        if(userToUnfollow == null || userId == userToUnfollowId)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        for(User elem : user.getFollowingList()){
            if(elem.getId() == userToUnfollowId){
                foundUserToUnfollowInFollowingList = true;
                break;
            }
        }
        if(foundUserToUnfollowInFollowingList){
            user.unfollowUser(userToUnfollow);
            userRepository.save(user);
            userRepository.save(userToUnfollow);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(new ErrorHandlingDTO("You no longer follow this user"),HttpStatus.BAD_REQUEST);
        }

    }


}
