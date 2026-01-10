package com.tanloc.lohu.lohuelearningadminapp.service;

import com.tanloc.lohu.lohuelearningadminapp.entity.Admin;
import com.tanloc.lohu.lohuelearningadminapp.repository.AdminRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AdminService {
    AdminRepository adminRepository;

    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin getByUsername(String username) {
        return adminRepository.findByUsername(username).orElse(null);
    }
}
