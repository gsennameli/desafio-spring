package com.example.desafioSpring.controllers;

import com.example.desafioSpring.dtos.CreateUserRequest;
import com.example.desafioSpring.dtos.UserDTO;
import com.example.desafioSpring.models.User;
import com.example.desafioSpring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public List<UserDTO> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@PathVariable int userId){
        return userService.findUserById(userId);
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity followUser(@PathVariable int userId, @PathVariable int userIdToFollow){
        //TODO Talvez mostrar mensagem quando um usuario tentar seguir um usuario que ele ja segue ou quando tenta seguir a si mesmo
        return userService.followUser(userId,userIdToFollow);
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity getQtyFollowers(@PathVariable int userId){
        return userService.qtyFollowers(userId);
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity getAllFollowers(@PathVariable int userId,@RequestParam(name = "order", required = false) String order){
        return userService.listFollowers(userId,order);
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity(userService.createUser(createUserRequest),HttpStatus.OK);
    }

    @GetMapping("/{userID}/followed/list")
    public ResponseEntity getAllFollowed(@PathVariable int userID,@RequestParam(name = "order", required = false) String order){
        return userService.listFollowing(userID,order);
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity unfollowUser(@PathVariable int userId, @PathVariable int userIdToUnfollow){
        return userService.unfollowUser(userId,userIdToUnfollow);
    }


}
