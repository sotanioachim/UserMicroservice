package com.usermanagerservice.UserManagerService.dtos;

import com.usermanagerservice.UserManagerService.models.User;

public class LoginResponseDTO {
    private UserDetailsDTO user;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(UserDetailsDTO user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public UserDetailsDTO getUser() {
        return user;
    }

    public void setUser(UserDetailsDTO user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
