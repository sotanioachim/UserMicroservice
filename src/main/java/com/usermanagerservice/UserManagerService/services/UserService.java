package com.usermanagerservice.UserManagerService.services;

import com.usermanagerservice.UserManagerService.dtos.UserDetailsDTO;
import com.usermanagerservice.UserManagerService.dtos.UserToDeviceDTO;
import com.usermanagerservice.UserManagerService.dtos.builders.UserBuilder;
import com.usermanagerservice.UserManagerService.models.User;
import com.usermanagerservice.UserManagerService.repositoty.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final WebClient webClient;

    @Autowired
    public UserService(PasswordEncoder encoder, UserRepository userRepository, WebClient.Builder webClientBuilder){
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8000").build();
    }


    public List<UserDetailsDTO> getUsers(){
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDetailsDTO)
                .collect(Collectors.toList());
    }

    public UserDetailsDTO getUserById(Long userId){
        User user = userRepository.findById(userId).get();
        return UserBuilder.toUserDetailsDTO(user);
    }

    @Transactional
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);

        webClient.delete()
                .uri("/user/{userId}",userId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }

    public UserDetailsDTO updateUser(UserDetailsDTO updateUser){
        User user = userRepository.findById(updateUser.getUserId()).get();
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setEmail(updateUser.getEmail());
        user.setDateOfBirth(updateUser.getDateOfBirth());

        userRepository.save(user);

        return UserBuilder.toUserDetailsDTO(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }
}
