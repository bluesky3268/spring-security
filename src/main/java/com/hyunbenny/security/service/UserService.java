package com.hyunbenny.security.service;

import com.hyunbenny.security.domain.User;
import com.hyunbenny.security.dto.UserDto;
import com.hyunbenny.security.exception.UserExistException;
import com.hyunbenny.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void join(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.userId());
        if(optionalUser.isPresent()) throw new UserExistException();

        String encryptedPassword = passwordEncoder.encode(userDto.password());
        userRepository.save(userDto.toEntity(encryptedPassword));
    }
}
