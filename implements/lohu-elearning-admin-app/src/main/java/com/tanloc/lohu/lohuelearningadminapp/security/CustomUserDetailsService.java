package com.tanloc.lohu.lohuelearningadminapp.security;

import com.tanloc.lohu.lohuelearningadminapp.entity.Admin;
import com.tanloc.lohu.lohuelearningadminapp.exception.AdminNotFoundException;
import com.tanloc.lohu.lohuelearningadminapp.repository.AdminRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username).orElseThrow(
                () -> new AdminNotFoundException("Không tìm thấy user")
        );

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())
                .build();
    }
}
