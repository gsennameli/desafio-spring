package com.example.desafioSpring.controllers;

import com.example.desafioSpring.models.User;
import com.example.desafioSpring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public Iterable<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity followUser(@PathVariable Long userId, @PathVariable Long userIdToFollow){
        //TODO Talvez mostrar mensagem quando um usuario tentar seguir um usuario que ele ja segue ou quando tenta seguir a si mesmo
        return userService.followUser(userId,userIdToFollow);
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity getQtyFollowers(@PathVariable Long userId){
        return new ResponseEntity(userService.qtyFollowers(userId),HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity getAllFollowers(@PathVariable Long userId){
        return new ResponseEntity(userService.listFollowers(userId),HttpStatus.OK);
    }

    @PostMapping("/create/{userName}")
    public ResponseEntity createUser(@PathVariable String userName){
        return new ResponseEntity(userService.createUser(userName),HttpStatus.OK);
    }

}
