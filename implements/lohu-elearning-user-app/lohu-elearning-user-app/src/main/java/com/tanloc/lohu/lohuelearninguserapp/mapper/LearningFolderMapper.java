package com.tanloc.lohu.lohuelearninguserapp.mapper;

import com.tanloc.lohu.lohuelearninguserapp.dto.LearningFolderCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.LearningFolder;
import com.tanloc.lohu.lohuelearninguserapp.entity.User;
import com.tanloc.lohu.lohuelearninguserapp.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LearningFolderMapper {
    public LearningFolder toLearningFolder(LearningFolderCreationRequest learningFolderCreationRequest, User user) {
        return LearningFolder.builder()
                .name(learningFolderCreationRequest.getName())
                .description(learningFolderCreationRequest.getDescription())
                .modifiedDate(LocalDateTime.now())
                .isPublic(learningFolderCreationRequest.getIsPublic())
                .user(user)
                .build();
    }
}
