package com.tanloc.lohu.lohuelearninguserapp.service;

import com.tanloc.lohu.lohuelearninguserapp.dto.FlashCardCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.dto.FlashCardEditRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCard;
import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCardSet;
import com.tanloc.lohu.lohuelearninguserapp.entity.User;
import com.tanloc.lohu.lohuelearninguserapp.exception.FlashCardNotFoundException;
import com.tanloc.lohu.lohuelearninguserapp.exception.FlashCardSetNotFoundException;
import com.tanloc.lohu.lohuelearninguserapp.exception.ImageUploadException;
import com.tanloc.lohu.lohuelearninguserapp.infrastructure.FileUploader;
import com.tanloc.lohu.lohuelearninguserapp.mapper.FlashCardMapper;
import com.tanloc.lohu.lohuelearninguserapp.repository.FlashCardRepository;
import com.tanloc.lohu.lohuelearninguserapp.repository.FlashCardSetRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FlashCardService {
    FlashCardRepository flashCardRepository;
    FlashCardSetRepository flashCardSetRepository;
    FileUploader fileUploader;
    FlashCardMapper flashCardMapper;

    @PreAuthorize("authentication.principal.user.id == #userId")
    public Page<FlashCard> getByUserIdAndFlashCardSetId(Long userId, Long flashCardSetId, int pageNumber, int pageSize, String sortBy) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy));
        return flashCardRepository.findByFlashCardSet_IdAndFlashCardSet_User_Id(flashCardSetId, userId, pageable);
    }

    @PreAuthorize("authentication.principal.user.id == #userId")
    public Page<FlashCard> getByFlashCardIdAndUserIdAndSearchKey(Long flashCardSetId, Long userId, String searchKey, int pageNumber, int pageSize) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return flashCardRepository.findByFlashCardSetIdAndUserIdAndSearchKey(flashCardSetId, userId, searchKey, pageable);
    }

    @PreAuthorize("authentication.principal.user.id == #user.id")
    public FlashCard create(FlashCardCreationRequest flashCardCreationRequest, User user) {
        FlashCardSet flashCardSet = flashCardSetRepository.findByIdAndUserId(flashCardCreationRequest.getFlashCardSetId(), user.getId())
                .orElseThrow(() -> new FlashCardSetNotFoundException("Không tìm thấy bộ flash card có mã là " + flashCardCreationRequest.getFlashCardSetId() + " của user " + user.getId()));

        String image = null;
        MultipartFile imageFile = flashCardCreationRequest.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            try{
                image = fileUploader.uploadFile(imageFile, "images/");
            } catch (IOException e) {
                throw new ImageUploadException("Đã xảy ra lỗi upfile");
            }
        }

        FlashCard flashCard = flashCardMapper.toFlashCard(flashCardCreationRequest, image, flashCardSet);
        return flashCardRepository.save(flashCard);
    }

    @PreAuthorize("authentication.principal.user.id == #userId")
    public FlashCard getByIdAndUserId(Long id, Long userId) {
        return flashCardRepository.findByIdAndFlashCardSet_User_Id(id, userId).orElseThrow(
                () -> new FlashCardNotFoundException("Không tồn tại flash card có mã là " + id + " của user " + userId)
        );
    }

    @PreAuthorize("authentication.principal.user.id == #userId")
    public FlashCard edit(FlashCardEditRequest flashCardEditRequest, Long userId) {
        FlashCard flashCard = flashCardRepository.findByIdAndFlashCardSet_User_Id(flashCardEditRequest.getId(), userId)
                .orElseThrow(() -> new FlashCardNotFoundException("Không tồn tại flash card có mã là " + flashCardEditRequest.getId() + " của user " + userId)
        );
        flashCard.setTerm(flashCardEditRequest.getTerm());
        flashCard.setDefinition(flashCardEditRequest.getDefinition());

        MultipartFile imageFile = flashCardEditRequest.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String image = fileUploader.uploadFile(imageFile, "images/");
                flashCard.setImage(image);
            } catch (IOException e) {
                throw new ImageUploadException("Đã xảy ra lỗi upload file");
            }
        }
        return flashCardRepository.save(flashCard);
    }
}
