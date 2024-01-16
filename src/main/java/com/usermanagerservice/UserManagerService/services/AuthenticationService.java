package com.usermanagerservice.UserManagerService.services;

import com.usermanagerservice.UserManagerService.dtos.LoginResponseDTO;
import com.usermanagerservice.UserManagerService.dtos.RegistrationDTO;
import com.usermanagerservice.UserManagerService.dtos.UserDetailsDTO;
import com.usermanagerservice.UserManagerService.dtos.UserToDeviceDTO;
import com.usermanagerservice.UserManagerService.dtos.builders.UserBuilder;
import com.usermanagerservice.UserManagerService.models.Role;
import com.usermanagerservice.UserManagerService.models.User;
import com.usermanagerservice.UserManagerService.models.exceptions.LoginFailedException;
import com.usermanagerservice.UserManagerService.repositoty.RoleRepository;
import com.usermanagerservice.UserManagerService.repositoty.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    private WebClient webClient;

    public AuthenticationService(WebClient webClient){
        this.webClient = webClient;
    }

    @Transactional
    public UserDetailsDTO registerUser(RegistrationDTO user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        User newUser = UserBuilder.toEntity(user);
        newUser.setPassword(encodedPassword);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);

        //Send user to the device manager
        User savedUser = userRepository.findByUsername(user.getUsername()).get();
        UserToDeviceDTO userDTO = new UserToDeviceDTO(savedUser.getUserId(),savedUser.getFirstName() + " " + savedUser.getLastName());
        System.out.println(userDTO);
        webClient.post()
                //.uri("/user")
                .body(BodyInserters.fromValue(userDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        return UserBuilder.toUserDetailsDTO(newUser);
    }

    public LoginResponseDTO loginUser(String username, String password){
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(UserBuilder.toUserDetailsDTO(userRepository.findByUsername(username).get()),token);

        }catch (AuthenticationException e){
            throw new LoginFailedException("Username or password incorect");
        }
    }
}
