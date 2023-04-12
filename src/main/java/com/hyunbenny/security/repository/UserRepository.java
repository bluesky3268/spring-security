package com.hyunbenny.security.repository;

import com.hyunbenny.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
