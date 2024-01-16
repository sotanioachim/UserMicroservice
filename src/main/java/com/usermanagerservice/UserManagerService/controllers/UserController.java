package com.usermanagerservice.UserManagerService.controllers;

import com.usermanagerservice.UserManagerService.dtos.UserDetailsDTO;
import com.usermanagerservice.UserManagerService.models.User;
import com.usermanagerservice.UserManagerService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getUsers(){
        return  new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping(path="/{userId}")
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable Long userId){
        return  new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDetailsDTO> updateUser(@RequestBody UserDetailsDTO updateUser) {
        return new ResponseEntity<>(userService.updateUser(updateUser), HttpStatus.OK );
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
}
