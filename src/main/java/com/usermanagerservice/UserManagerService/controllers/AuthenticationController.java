package com.usermanagerservice.UserManagerService.controllers;

import com.usermanagerservice.UserManagerService.dtos.LoginDTO;
import com.usermanagerservice.UserManagerService.dtos.LoginResponseDTO;
import com.usermanagerservice.UserManagerService.dtos.RegistrationDTO;
import com.usermanagerservice.UserManagerService.dtos.UserDetailsDTO;
import com.usermanagerservice.UserManagerService.models.User;
import com.usermanagerservice.UserManagerService.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserDetailsDTO registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body);
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
