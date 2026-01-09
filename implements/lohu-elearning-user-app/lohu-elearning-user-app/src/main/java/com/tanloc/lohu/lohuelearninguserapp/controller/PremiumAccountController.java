package com.tanloc.lohu.lohuelearninguserapp.controller;

import com.tanloc.lohu.lohuelearninguserapp.security.CustomUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("user/premium")
public class PremiumAccountController {

    @GetMapping("/about")
    public String showPremiumSubscription(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        model.addAttribute("user", customUserDetails.getUser());
        return "premium-subscription";
    }
}
