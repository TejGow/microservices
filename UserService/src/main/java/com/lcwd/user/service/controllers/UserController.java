package com.lcwd.user.service.controllers;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User userResponse = userService.saveUser(user);
       return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User userResponse = userService.getUser(userId);
        return ResponseEntity.ok(userResponse);
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
       List<User> users = userService.getAUsers();
       return ResponseEntity.ok(users);
    }
}
