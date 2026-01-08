package com.tanloc.lohu.lohuelearninguserapp.mapper;

import com.tanloc.lohu.lohuelearninguserapp.dto.FlashCardSetCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.dto.FlashCardSetEditRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCardSet;
import com.tanloc.lohu.lohuelearninguserapp.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FlashCardSetMapper {
    public FlashCardSet toFlashCardSet(FlashCardSetCreationRequest flashCardSetCreationRequest, User user) {
        return FlashCardSet.builder()
                .name(flashCardSetCreationRequest.getName())
                .description(flashCardSetCreationRequest.getDescription())
                .modifiedDate(LocalDateTime.now())
                .isPublic(flashCardSetCreationRequest.getIsPublic())
                .user(user)
                .build();
    }

    public FlashCardSet toFlashCardSet(FlashCardSetEditRequest flashCardSetEditRequest, User user) {
        return com.tanloc.lohu.lohuelearninguserapp.entity.FlashCardSet.builder()
                .id(flashCardSetEditRequest.getId())
                .name(flashCardSetEditRequest.getName())
                .description(flashCardSetEditRequest.getDescription())
                .modifiedDate(LocalDateTime.now())
                .isPublic(flashCardSetEditRequest.getIsPublic())
                .user(user)
                .build();

    }
}
