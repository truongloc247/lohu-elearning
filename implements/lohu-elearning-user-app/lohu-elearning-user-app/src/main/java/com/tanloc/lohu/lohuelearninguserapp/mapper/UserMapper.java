package com.tanloc.lohu.lohuelearninguserapp.mapper;

import com.tanloc.lohu.lohuelearninguserapp.dto.UserCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.User;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class UserMapper {
    public User toUser(UserCreationRequest userCreationRequest) {
        User user = User.builder()
                .name(userCreationRequest.getName())
                .gender(userCreationRequest.getGender())
                .dateOfBirth(userCreationRequest.getDateOfBirth())
                .phoneNumber(userCreationRequest.getPhoneNumber())
                .email(userCreationRequest.getEmail())
                .address(userCreationRequest.getAddress())
                .username(userCreationRequest.getUsername())
                .password(userCreationRequest.getPassword())
                .build();

        user.setAccountType(false);
        return user;
    }
}
