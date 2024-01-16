package com.usermanagerservice.UserManagerService.dtos;

public class UserToDeviceDTO {
    private Long userId;
    private String name;

    public UserToDeviceDTO() {
    }

    public UserToDeviceDTO(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
