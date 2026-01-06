package com.tanloc.lohu.lohuelearninguserapp.controller;

import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCardSet;
import com.tanloc.lohu.lohuelearninguserapp.security.CustomUserDetails;
import com.tanloc.lohu.lohuelearninguserapp.service.FlashCardSetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/user/library/flashCardSet")
public class FlashCardSetController {
    FlashCardSetService flashCardSetService;

    @GetMapping("/all")
    public String showFlashCardSets(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber, @RequestParam(name = "searchKey", required = false) String searchKey, Model model) {
        Page<FlashCardSet> flashCardSets;
        if (searchKey != null) {
            flashCardSets = flashCardSetService.getByUserIdAndNameContaining(customUserDetails.getUser().getId(), searchKey, pageNumber, 4, "name");
            model.addAttribute("searchKey", searchKey);
        }
        else {
            flashCardSets = flashCardSetService.getByUserId(customUserDetails.getUser().getId(), pageNumber, 4, "name");
        }
        model.addAttribute("flashCardSets", flashCardSets.getContent());
        model.addAttribute("currentPage", flashCardSets.getNumber() + 1);
        model.addAttribute("totalPages", flashCardSets.getTotalPages());
        return "flash-card-sets";
    }


}
