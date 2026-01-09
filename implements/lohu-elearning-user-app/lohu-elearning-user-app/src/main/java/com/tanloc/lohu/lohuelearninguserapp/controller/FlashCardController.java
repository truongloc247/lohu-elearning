package com.tanloc.lohu.lohuelearninguserapp.controller;

import com.tanloc.lohu.lohuelearninguserapp.dto.FlashCardCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.dto.FlashCardEditRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCard;
import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCardSet;
import com.tanloc.lohu.lohuelearninguserapp.exception.FlashCardNotFoundException;
import com.tanloc.lohu.lohuelearninguserapp.exception.ImageUploadException;
import com.tanloc.lohu.lohuelearninguserapp.security.CustomUserDetails;
import com.tanloc.lohu.lohuelearninguserapp.service.FlashCardService;
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
@RequestMapping("/user/flashCardSet")
public class FlashCardController {
    FlashCardService flashCardService;
    FlashCardSetService flashCardSetService;

    @GetMapping("/{flashCardSetId}")
    public String showAllFlashCardsOfSet(@PathVariable("flashCardSetId") Long flashCardSetId, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber, @RequestParam(name = "searchKey", required = false) String searchKey, Model model) {
        Page<FlashCard> flashCards;
        if (searchKey != null) {
            flashCards = flashCardService.getByFlashCardIdAndUserIdAndSearchKey(flashCardSetId, customUserDetails.getUser().getId(), searchKey, pageNumber, 4);
            model.addAttribute("searchKey", searchKey);
        }
        else {
            flashCards = flashCardService.getByUserIdAndFlashCardSetId(customUserDetails.getUser().getId(), flashCardSetId, pageNumber, 4, "id");
        }
        FlashCardSet flashCardSet = flashCardSetService.getByIdAndUserId(flashCardSetId, customUserDetails.getUser().getId());
        model.addAttribute("flashCardSet", flashCardSet);
        model.addAttribute("flashCards", flashCards.getContent());
        model.addAttribute("currentPage", flashCards.getNumber() + 1);
        model.addAttribute("totalPages", flashCards.getTotalPages());
        return "flash-cards";
    }

    @GetMapping("/{flashCardSetId}/flashCard/create")
    public String showFlashCardCreationForm(@PathVariable("flashCardSetId") Long flashCardSetId, Model model) {
        FlashCardCreationRequest flashCardCreationRequest = FlashCardCreationRequest.builder()
                .flashCardSetId(flashCardSetId)
                .build();
        model.addAttribute("flashCardCreationRequest", flashCardCreationRequest);
        return "flash-card-creation-form";
    }

    @PostMapping("/{flashCardSetId}/flashCard/doCreate")
    public String create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @ModelAttribute("flashCardCreationRequest") FlashCardCreationRequest flashCardCreationRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "flash-card-creation-form";
        }
        try {
            flashCardService.create(flashCardCreationRequest, customUserDetails.getUser());
        } catch (ImageUploadException e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("flashCardCreationRequest", flashCardCreationRequest);
            return "flash-card-creation-form";
        }

        redirectAttributes.addFlashAttribute("message", "Tạo mới flash card thành công");
        return "redirect:/user/flashCardSet/" + flashCardCreationRequest.getFlashCardSetId();
    }

    @GetMapping("/{flashCardSetId}/flashCard/edit/{flashCardId}")
    public String showFlashCardEditForm(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("flashCardId") Long flashCardId, @PathVariable("flashCardSetId") Long flashCardSetId, Model model) {
        FlashCard flashCard = flashCardService.getByIdAndUserId(flashCardId, customUserDetails.getUser().getId());
        if (flashCard.getFlashCardSet().getId() != flashCardSetId) {
            throw new FlashCardNotFoundException("Không tồn tại flash card có mã là " + flashCardId + " thuộc bộ flash card " + flashCardSetId);
        }
        FlashCardEditRequest flashCardEditRequest = FlashCardEditRequest.builder()
                .id(flashCard.getId())
                .term(flashCard.getTerm())
                .definition(flashCard.getDefinition())
                .build();
        model.addAttribute("flashCardSetId", flashCard.getFlashCardSet().getId());
        model.addAttribute("flashCardEditRequest", flashCardEditRequest);
        return "flash-card-edit-form";
    }

    @PostMapping("/{flashCardSetId}/flashCard/doEdit")
    public String edit(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @ModelAttribute("flashCardEditRequest") FlashCardEditRequest flashCardEditRequest, BindingResult bindingResult, @PathVariable("flashCardSetId") Long flashCardSetId, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.print("wtf");
            model.addAttribute("flashCardSetId", flashCardSetId);
            return "flash-card-edit-form";
        }
        try {
            FlashCard flashCard = flashCardService.edit(flashCardEditRequest, customUserDetails.getUser().getId());
            redirectAttributes.addFlashAttribute("message", "Đã cập nhật thành công");
            return "redirect:/user/flashCardSet/" + flashCard.getFlashCardSet().getId();
        } catch (ImageUploadException e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("flashCardEditRequest", flashCardEditRequest);
            model.addAttribute("flashCardSetId", flashCardSetId);
            return "flash-card-edit-form";
        }
    }

    @GetMapping("/{flashCardSetId}/flashCard/delete/{flashCardId}")
    public String delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("flashCardId") Long flashCardId, @PathVariable("flashCardSetId") Long flashCardSetId, RedirectAttributes redirectAttributes) {
        FlashCard flashCard = flashCardService.getByIdAndUserId(flashCardId, customUserDetails.getUser().getId());
        if (flashCard.getFlashCardSet().getId() != flashCardSetId) {
            throw new FlashCardNotFoundException("Không tồn tại flash card có mã là " + flashCardId + " thuộc bộ flash card " + flashCardSetId);
        }

        flashCardService.delete(flashCardId, customUserDetails.getUser().getId());
        redirectAttributes.addFlashAttribute("message", "Đã xóa thành công");
        return "redirect:/user/flashCardSet/" + flashCardSetId;
    }
}
