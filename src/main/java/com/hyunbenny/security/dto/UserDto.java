package com.hyunbenny.security.dto;

import com.hyunbenny.security.domain.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record UserDto(
        String userId,
        String password,
        String email,
        String name,
        Timestamp createdAt
) {

    public static UserDto of(String userId, String password, String email, String name) {
        return new UserDto(userId, password, email, name, Timestamp.valueOf(LocalDateTime.now()));
    }

    public static UserDto from(User user) {
        return new UserDto(user.getUserId(), user.getPassword(), user.getEmail(), user.getName(), user.getCreatedAt());
    }

    public User toEntity(String encryptedPassword) {
        return User.of(userId, encryptedPassword, email, name, null);
    }
}
