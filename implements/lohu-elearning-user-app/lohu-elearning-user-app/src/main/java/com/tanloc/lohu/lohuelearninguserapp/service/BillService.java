package com.tanloc.lohu.lohuelearninguserapp.service;

import com.tanloc.lohu.lohuelearninguserapp.entity.Bill;
import com.tanloc.lohu.lohuelearninguserapp.entity.User;
import com.tanloc.lohu.lohuelearninguserapp.exception.UserNotFoundException;
import com.tanloc.lohu.lohuelearninguserapp.repository.BillRepository;
import com.tanloc.lohu.lohuelearninguserapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BillService {
    @Value("${lohu.premium.price}")
    Long price;

    final BillRepository billRepository;
    final UserRepository userRepository;

    @PreAuthorize("authentication.principal.user.id == #user.id")
    public Bill create(User user) {
        User foundUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new UserNotFoundException("Không tồn tại user có mã " + user.getId())
        );

        Bill bill = Bill.builder()
                .creationDate(LocalDateTime.now())
                .amount(price)
                .isPurchased(false)
                .description("")
                .user(foundUser)
                .build();

        return billRepository.save(bill);
    }
}
