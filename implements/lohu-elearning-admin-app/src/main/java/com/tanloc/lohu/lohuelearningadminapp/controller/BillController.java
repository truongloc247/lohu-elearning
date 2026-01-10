package com.tanloc.lohu.lohuelearningadminapp.controller;

import com.tanloc.lohu.lohuelearningadminapp.entity.Bill;
import com.tanloc.lohu.lohuelearningadminapp.service.BillService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BillController {
    BillService billService;

    @GetMapping("/bills")
    public String showAllBills(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber, @RequestParam(name = "searchDate", required = false) LocalDate searchDate, Model model) {
        Page<Bill> bills;
        if (searchDate != null) {
            System.out.print(searchDate);
            LocalDateTime startDate = searchDate.atStartOfDay();
            LocalDateTime endDate = searchDate.atTime(LocalTime.MAX);
            bills = billService.getByCreationDate(startDate, endDate, pageNumber, 10, "creationDate");
            model.addAttribute("searchDate", searchDate.toString());
        }
        else {
            bills = billService.getAll(pageNumber, 10, "creationDate");
        }
        model.addAttribute("bills", bills.getContent());
        model.addAttribute("currentPage", bills.getNumber() + 1);
        model.addAttribute("totalPages", bills.getTotalPages());
        return "bills";
    }

    @GetMapping("/bills/confirmBill/{id}")
    public String confirmBill(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            billService.confirmBill(id);
            redirectAttributes.addFlashAttribute("message", "Xác nhận hóa đơn thành công");
        }
        catch (MessagingException e) {
            redirectAttributes.addFlashAttribute("emailErrorMessage", "Đã xảy ra lỗi trong quá trình xác nhận");
        }
        return "redirect:/admin/bills";
    }

    @GetMapping("/monthlyRevenue")
    public String showMonthRevenue(Model model) {
        int year = LocalDate.now().getYear();
        List<Long> monthlyRevenue = billService.getMonthlyRevenue(year);

        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("currentYear", year);
        return "monthly-revenue";
    }
}
