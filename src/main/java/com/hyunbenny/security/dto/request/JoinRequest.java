package com.hyunbenny.security.dto.request;

import com.hyunbenny.security.domain.User;
import com.hyunbenny.security.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinRequest {
    private String userId;
    private String password;
    private String email;

    @Builder
    public JoinRequest(String userId, String password, String email) {
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public UserDto toUserDto() {
        return UserDto.of(userId, password, email);
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
