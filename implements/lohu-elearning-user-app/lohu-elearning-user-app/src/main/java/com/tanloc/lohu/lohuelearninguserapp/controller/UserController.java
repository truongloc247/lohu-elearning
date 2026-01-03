package com.tanloc.lohu.lohuelearninguserapp.controller;

import com.tanloc.lohu.lohuelearninguserapp.dto.UserCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.exception.PasswordMismatchException;
import com.tanloc.lohu.lohuelearninguserapp.exception.UserCreationException;
import com.tanloc.lohu.lohuelearninguserapp.service.UserService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String showUserCreationForm(Model model, HttpSession session) {
        if (session.getAttribute("verifiedEmail") == null) {
            throw new UserCreationException("Quá trình đăng ký thất bại");
        }
        UserCreationRequest userCreationRequest = new UserCreationRequest();
        userCreationRequest.setEmail((String) session.getAttribute("verifiedEmail"));
        model.addAttribute("userCreationRequest", userCreationRequest);
        return "user-creation-form";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("userCreationRequest") UserCreationRequest userCreationRequest, BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        if (!userCreationRequest.getEmail().equals(session.getAttribute("verifiedEmail"))) {
            throw new UserCreationException("Quá trình đăng ký thất bại");
        }
        if (bindingResult.hasErrors()) {
            return "user-creation-form";
        }
        try {
            userService.createUser(userCreationRequest);
        } catch (PasswordMismatchException e) {
            model.addAttribute("message", e.getMessage());
            return "user-creation-form";
        }
        redirectAttributes.addFlashAttribute("message", "Tạo tài khoản thành công!");
        return "redirect:/login";
    }
}
