package com.hyunbenny.security.dto.request;

import com.hyunbenny.security.dto.UserDto;

public record JoinRequest(
        String userId,
        String password,
        String email,
        String name
) {

    public UserDto toUserDto() {
        return UserDto.of(userId, password, email, name);
    }

    @Override
    public String toString() {
        return "JoinRequest{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
