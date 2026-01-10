package com.tanloc.lohu.lohuelearningadminapp.configuration;

import com.tanloc.lohu.lohuelearningadminapp.entity.Admin;
import com.tanloc.lohu.lohuelearningadminapp.service.AdminService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    AdminService adminService;
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Admin foundAdmin = adminService.getByUsername("admin2204");
        if (foundAdmin != null) return;
        Admin admin = Admin.builder()
                .username("admin2204")
                .password(passwordEncoder.encode("admin2204"))
                .build();

        adminService.create(admin);
    }
}
