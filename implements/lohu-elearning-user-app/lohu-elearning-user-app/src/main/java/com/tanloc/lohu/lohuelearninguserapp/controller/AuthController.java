package com.tanloc.lohu.lohuelearninguserapp.controller;

import com.tanloc.lohu.lohuelearninguserapp.dto.EmailVerificationRequest;
import com.tanloc.lohu.lohuelearninguserapp.dto.TokenVerificationRequest;
import com.tanloc.lohu.lohuelearninguserapp.exception.EmailAlreadyExistsException;
import com.tanloc.lohu.lohuelearninguserapp.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;

    @GetMapping("/verifyEmail")
    public String showEmailVerificationForm(Model model) {
        model.addAttribute("emailVerificationRequest", new EmailVerificationRequest());
        return "email-verification-form";
    }

    @PostMapping("/sendEmail")
    public String verifyEmail(@Valid @ModelAttribute("emailVerificationRequest") EmailVerificationRequest emailVerificationRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "email-verification-form";
        }
        try {
            authService.verifyEmail(emailVerificationRequest);
        } catch (MessagingException e) {
            model.addAttribute("message", "Gửi email không thành công, vui lòng thử lại!");
            return "email-verification-form";
        }
        catch (EmailAlreadyExistsException e) {
            model.addAttribute("message", "Email đã tồn tại");
            return "email-verification-form";
        }
        model.addAttribute("message", "Vui lòng truy cập email để xác nhận đăng ký tài khoản!");
        return "email-verification-form";
    }

    @GetMapping("/verifyToken")
    public String verifyToken(@Valid @ModelAttribute("tokenVerificationRequest") TokenVerificationRequest tokenVerificationRequest, HttpSession session) {
        String verifiedEmail = authService.verifyToken(tokenVerificationRequest).getEmail();
        session.setAttribute("verifiedEmail", verifiedEmail);
        return "redirect:/user/register";
    }
}
