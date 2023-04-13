package com.hyunbenny.security.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
public class User {

    @Id
    private String userId;

    private String password;

    private String email;

    private String name;

    private String roles; // USER, MANAGER, ADMIN

    // oauth 로그인을 위해 추가
    private String provider;
    private String providerId;

    @CreationTimestamp
    private Timestamp createdAt;

    protected User() {}

    private User(String userId, String password, String email, String name, String roles, String provider, String providerId) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.name = name;

        if(roles == null || roles.equals("")) this.roles = "ROLE_USER";
        else this.roles = roles;

        this.provider = provider;
        this.providerId = providerId;
//        if(provider == null || provider.equals("")) this.provider = "hyunbenny";
//        else this.provider = provider;
//
//        if(providerId == null || providerId.equals("")) this.providerId = userId;
//        else this.providerId = providerId;

        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public static User of(String userId, String password, String email, String name, String roles) {
        return new User(userId, password, email, name, roles, null, null);
    }

    public static User of(String userId,String email, String name, String roles, String provider, String providerId) {
        return new User(userId, null, email, name, roles, provider, providerId);
    }

    public void updateEmail(String newEmail) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + roles + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
