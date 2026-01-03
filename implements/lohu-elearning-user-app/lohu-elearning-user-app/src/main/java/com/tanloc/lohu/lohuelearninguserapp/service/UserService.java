package com.tanloc.lohu.lohuelearninguserapp.service;

import com.tanloc.lohu.lohuelearninguserapp.dto.UserCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.User;
import com.tanloc.lohu.lohuelearninguserapp.exception.PasswordMismatchException;
import com.tanloc.lohu.lohuelearninguserapp.mapper.UserMapper;
import com.tanloc.lohu.lohuelearninguserapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest userCreationRequest) {
        if (!userCreationRequest.getPassword().equals(userCreationRequest.getConfirmPassword())) {
            throw new PasswordMismatchException("Xác nhận mật khẩu không khớp");
        }
        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
