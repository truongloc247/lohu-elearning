package com.tanloc.lohu.lohuelearninguserapp.controller;

import com.tanloc.lohu.lohuelearninguserapp.dto.FlashCardSetCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.dto.FlashCardSetEditRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCardSet;
import com.tanloc.lohu.lohuelearninguserapp.security.CustomUserDetails;
import com.tanloc.lohu.lohuelearninguserapp.service.FlashCardSetService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("user/flashCardSet")
public class FlashCardSetController {
    FlashCardSetService flashCardSetService;

    @GetMapping("/all")
    public String showFlashCardSets(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber, @RequestParam(value = "searchKey", required = false) String searchKey, Model model) {
        Page<FlashCardSet> flashCardSets;
        if (searchKey != null) {
            flashCardSets = flashCardSetService.getUserIdAndByNameContaining(customUserDetails.getUser().getId(), searchKey, pageNumber, 4, "name");
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

    @GetMapping("/create")
    public String showFlashCardSetCreationForm(Model model) {
        model.addAttribute("flashCardSetCreationRequest", new FlashCardSetCreationRequest());
        return "flash-card-set-creation-form";
    }

    @PostMapping("/doCreate")
    public String create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @ModelAttribute("flashCardSetCreationRequest") FlashCardSetCreationRequest flashCardSetCreationRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "flash-card-set-creation-form";
        }
        flashCardSetService.add(customUserDetails.getUser(), flashCardSetCreationRequest);
        redirectAttributes.addFlashAttribute("message", "Tạo thư mục bộ flash card mới thành công");
        return "redirect:/user/flashCardSet/all";
    }

    @GetMapping("/edit/{id}")
    public String showFlashCardSetEditForm(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("id") Long folderId, Model model) {
        FlashCardSet flashCardSet = flashCardSetService.getByIdAndUserId(folderId, customUserDetails.getUser().getId());
        FlashCardSetEditRequest flashCardSetEditRequest = FlashCardSetEditRequest.builder()
                .id(flashCardSet.getId())
                .name(flashCardSet.getName())
                .description(flashCardSet.getDescription())
                .isPublic(flashCardSet.getIsPublic())
                .build();
        model.addAttribute("flashCardSetEditRequest", flashCardSetEditRequest);
        return "flash-card-set-edit-form";
    }

    @PostMapping("/doEdit")
    public String edit(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @ModelAttribute("flashCardSetEditRequest") FlashCardSetEditRequest flashCardSetEditRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "flash-card-set-edit-form";
        }
        flashCardSetService.edit(customUserDetails.getUser(), flashCardSetEditRequest);
        redirectAttributes.addFlashAttribute("message", "cập nhật thư mục học tập thành công");
        return "redirect:/user/flashCardSet/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (flashCardSetService.delete(customUserDetails.getUser(), id) == 0) {
            redirectAttributes.addFlashAttribute("message", "Xóa bộ flash card thành công");
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Xóa không thành công");
        }
        return "redirect:/user/flashCardSet/all";
    }
}
