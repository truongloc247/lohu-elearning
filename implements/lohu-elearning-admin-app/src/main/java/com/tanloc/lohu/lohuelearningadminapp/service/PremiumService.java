package com.tanloc.lohu.lohuelearningadminapp.service;

import com.tanloc.lohu.lohuelearningadminapp.entity.User;
import com.tanloc.lohu.lohuelearningadminapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PremiumService {
    UserRepository userRepository;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void checkPremium() {
        List<User> users = userRepository.findExpiredPremiumUsers(LocalDateTime.now());
        if (users.size() == 0) {
            return;
        }
        for (User user : users) {
            user.setAccountType(false);
            user.setVipExpirationDate(null);
        }

        userRepository.saveAll(users);
    }
}
