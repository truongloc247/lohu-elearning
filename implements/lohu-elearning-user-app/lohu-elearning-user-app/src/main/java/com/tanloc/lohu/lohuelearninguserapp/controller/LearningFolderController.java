package com.tanloc.lohu.lohuelearninguserapp.controller;

import com.tanloc.lohu.lohuelearninguserapp.dto.LearningFolderCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.LearningFolder;
import com.tanloc.lohu.lohuelearninguserapp.security.CustomUserDetails;
import com.tanloc.lohu.lohuelearninguserapp.service.LearningFolderService;
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
@RequestMapping("user/library/learningFolder")
public class LearningFolderController {
    LearningFolderService learningFolderService;

    @GetMapping("/all")
    public String showLearingFolder(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber, @RequestParam(value = "searchKey", required = false) String searchKey, Model model) {
        Page<LearningFolder> learningFolders;
        if (searchKey != null) {
            learningFolders = learningFolderService.getUserIdAndByNameContaining(customUserDetails.getUser().getId(), searchKey, pageNumber, 4, "name");
            model.addAttribute("searchKey", searchKey);
        }
        else {
            learningFolders = learningFolderService.getByUserId(customUserDetails.getUser().getId(), pageNumber, 4, "name");
        }
        model.addAttribute("learningFolders", learningFolders.getContent());
        model.addAttribute("currentPage", learningFolders.getNumber() + 1);
        model.addAttribute("totalPages", learningFolders.getTotalPages());
        return "learning-folders";
    }

    @GetMapping("/create")
    public String showFolderCreationForm(Model model) {
        model.addAttribute("learningFolderCreationRequest", new LearningFolderCreationRequest());
        return "learning-folder-creation-form";
    }

    @PostMapping("/doCreate")
    public String create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @ModelAttribute("learningFolderCreationRequest") LearningFolderCreationRequest learningFolderCreationRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "learning-folder-creation-form";
        }
        learningFolderService.add(customUserDetails.getUser(), learningFolderCreationRequest);
        redirectAttributes.addFlashAttribute("message", "Tạo thư mục học tập mới thành công");
        return "redirect:/user/library/learningFolder/all";
    }

//    @GetMapping("edit/{id}")
//    public String showFolderEditForm(@PathVariable("id") Long folderId, Model model) {
//        LearningFolder learningFolder = learningFolderService.getById(folderId);
//        LearningFolderCreationRequest learningFolderRequest = LearningFolderCreationRequest.builder()
//                .name(learningFolder.getName())
//                .description(learningFolder.getDescription())
//                .isPublic(learningFolder.getIsPublic())
//                .build();
//        model.addAttribute("learningFolderRequest", learningFolderRequest);
//        model.addAttribute("mode", "edit");
//        return "learning-folder-creation-form";
//    }


}
