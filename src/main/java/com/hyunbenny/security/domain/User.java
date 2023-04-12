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

    private String roles; // USER, MANAGER, ADMIN

    @CreationTimestamp
    private Timestamp createdAt;

    protected User() {}

    private User(String userId, String password, String email, String roles) {
        this.userId = userId;
        this.password = password;
        this.email = email;

        if(roles == null || roles.equals("")) this.roles = "ROLE_USER";
        else this.roles = roles;

        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public static User of(String userId, String password, String email, String roles) {
        return new User(userId, password, email, roles);
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
