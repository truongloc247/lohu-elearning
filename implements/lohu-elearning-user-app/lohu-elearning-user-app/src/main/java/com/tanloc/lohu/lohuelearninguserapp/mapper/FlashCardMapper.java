package com.tanloc.lohu.lohuelearninguserapp.mapper;

import com.tanloc.lohu.lohuelearninguserapp.dto.FlashCardCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCard;
import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCardSet;
import com.tanloc.lohu.lohuelearninguserapp.entity.User;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class FlashCardMapper {
    public FlashCard toFlashCard(FlashCardCreationRequest flashCardCreationRequest, String image, FlashCardSet flashCardSet) {
        return FlashCard.builder()
                .term(flashCardCreationRequest.getTerm())
                .definition(flashCardCreationRequest.getDefinition())
                .image(image)
                .flashCardSet(flashCardSet)
                .build();
    }
}
