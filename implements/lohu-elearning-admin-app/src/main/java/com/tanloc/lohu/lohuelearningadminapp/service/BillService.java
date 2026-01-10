package com.tanloc.lohu.lohuelearningadminapp.service;

import com.tanloc.lohu.lohuelearningadminapp.dto.MonthlyRevenue;
import com.tanloc.lohu.lohuelearningadminapp.entity.Bill;
import com.tanloc.lohu.lohuelearningadminapp.entity.User;
import com.tanloc.lohu.lohuelearningadminapp.exception.BillNotFoundException;
import com.tanloc.lohu.lohuelearningadminapp.infrastructure.EmailSender;
import com.tanloc.lohu.lohuelearningadminapp.repository.BillRepository;
import com.tanloc.lohu.lohuelearningadminapp.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BillService {
    BillRepository billRepository;
    UserRepository userRepository;
    EmailSender emailSender;

    public Page<Bill> getAll(int pageNumber, int pageSize, String sortBy) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).descending());
        return billRepository.findAll(pageable);
    }

    public Page<Bill> getByCreationDate(LocalDateTime startDate, LocalDateTime endDate, int pageNumber, int pageSize, String sortBy) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).descending());
        return billRepository.findByCreationDateBetween(startDate, endDate, pageable);
    }

    @Transactional
    public void confirmBill(Long id) throws MessagingException {
        Bill bill = billRepository.findById(id).orElseThrow(
                () -> new BillNotFoundException("Không tìm thấy hóa đơn có mã " + id)
        );

        if (bill.getIsPurchased() == true) {
            throw new BillNotFoundException("Bill đã được thanh toán");
        }

        bill.setIsPurchased(true);
        User user = bill.getUser();
        user.setAccountType(true);
        if (user.getVipExpirationDate() == null) {
            user.setVipExpirationDate(LocalDateTime.now().plusDays(30));
        }
        else {
            user.setVipExpirationDate(user.getVipExpirationDate().plusDays(30));
        }
        billRepository.save(bill);
        userRepository.save(user);

        emailSender.sendEmail(user.getEmail(), "Thông báo đăng ký thành công gói PREMIUM", "actived-premium-email", null);
    }

    public List<Long> getMonthlyRevenue(int year) {
        List<MonthlyRevenue> monthlyRevenueList = billRepository.getMonthlyRevenue(year);

        Map<Integer, Long> monthlyRevenueMap = new HashMap<>();
        for (MonthlyRevenue monthlyRevenue : monthlyRevenueList) {
            monthlyRevenueMap.put(monthlyRevenue.getMonth(), monthlyRevenue.getTotalMoney());
        }

        List<Long> revenueResult = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            revenueResult.add(monthlyRevenueMap.getOrDefault(i, 0L));
        }
        return revenueResult;
    }
}
