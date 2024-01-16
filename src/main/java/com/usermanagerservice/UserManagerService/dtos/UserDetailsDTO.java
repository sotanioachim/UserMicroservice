package com.usermanagerservice.UserManagerService.dtos;

import java.util.Date;

public class UserDetailsDTO {

    private Long userId;

    private String username;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String email;

    private String role;

    public UserDetailsDTO() {
        super();
    }

    public UserDetailsDTO(Long userId,String username,String firstName, String lastName, Date dateOfBirth, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.firstname = firstName;
        this.lastname = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.role = role;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
