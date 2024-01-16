package com.usermanagerservice.UserManagerService.dtos.builders;

import com.usermanagerservice.UserManagerService.dtos.RegistrationDTO;
import com.usermanagerservice.UserManagerService.dtos.UserDetailsDTO;
import com.usermanagerservice.UserManagerService.models.Role;
import com.usermanagerservice.UserManagerService.models.User;
import com.usermanagerservice.UserManagerService.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class UserBuilder {

    private static UserRepository userRepository;
    @Autowired
    private UserBuilder() {
        this.userRepository = userRepository;
    }

    public static UserDetailsDTO toUserDetailsDTO(User user) {
        Set<Role> roleSet = (Set<Role>) user.getAuthorities(); // Use Set instead of HashSet
        Role userRole = roleSet.isEmpty() ? null : roleSet.iterator().next();
        return new UserDetailsDTO(user.getUserId(),user.getUsername(),user.getFirstName(), user.getLastName(), user.getDateOfBirth(),user.getEmail(), userRole != null ? userRole.getAuthority() : null);
    }

    public static User toEntity(RegistrationDTO userRegisterDTO){
        User user = new User(
                0l,
                userRegisterDTO.getUsername(),
                userRegisterDTO.getPassword(),
                userRegisterDTO.getFirstname(),
                userRegisterDTO.getLastName(),
                "",
                userRegisterDTO.getDateOfBirth(),
                null
        );
        return user;
    }

    public static User toEntity(UserDetailsDTO userDetailsDTO) {
        User user = userRepository.findById(userDetailsDTO.getUserId()).get();
        return user;
    }
}
