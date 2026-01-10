package com.tanloc.lohu.lohuelearninguserapp.service;

import com.tanloc.lohu.lohuelearninguserapp.dto.FlashCardSetCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.dto.FlashCardSetEditRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCardSet;
import com.tanloc.lohu.lohuelearninguserapp.entity.User;
import com.tanloc.lohu.lohuelearninguserapp.exception.FlashCardNotFoundException;
import com.tanloc.lohu.lohuelearninguserapp.exception.FlashCardSetNotFoundException;
import com.tanloc.lohu.lohuelearninguserapp.exception.UserNotFoundException;
import com.tanloc.lohu.lohuelearninguserapp.mapper.FlashCardSetMapper;
import com.tanloc.lohu.lohuelearninguserapp.repository.FlashCardSetRepository;
import com.tanloc.lohu.lohuelearninguserapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FlashCardSetService {
    FlashCardSetRepository flashCardSetRepository;
    UserRepository userRepository;
    FlashCardSetMapper flashCardSetMapper;

    @PreAuthorize("authentication.principal.user.id == #userId")
    public Page<FlashCardSet> getByUserId(Long userId, int pageNumber, int pageSize, String sortBy) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).ascending());
        return flashCardSetRepository.findByUserId(userId, pageable);
    }

    @PreAuthorize("authentication.principal.user.id == #userId")
    public Page<FlashCardSet> getUserIdAndByNameContaining(Long userId, String name, int pageNumber, int pageSize, String sortBy) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).ascending());
        return flashCardSetRepository.findByUserIdAndNameContaining(userId, name, pageable);
    }

    @PreAuthorize("authentication.principal.user.id == #userId")
    public FlashCardSet getByIdAndUserId(Long id, Long userId) {
        return flashCardSetRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new FlashCardSetNotFoundException("Không tồn tại bộ flash card có id là " + id));
    }

    @PreAuthorize("authentication.principal.user.id == #user.id")
    public FlashCardSet add(User user, FlashCardSetCreationRequest flashCardSetCreationRequest) {
        User foundUser = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("Không tồn tại user có id là " + user.getId()));
        FlashCardSet flashCardSet = flashCardSetMapper.toFlashCardSet(flashCardSetCreationRequest, foundUser);
        return flashCardSetRepository.save(flashCardSet);
    }

    @PreAuthorize("authentication.principal.user.id == #user.id")
    public FlashCardSet edit(User user, FlashCardSetEditRequest flashCardSetEditRequest) {
        FlashCardSet foundFlashCardSet = flashCardSetRepository.findByIdAndUserId(flashCardSetEditRequest.getId(), user.getId())
                .orElseThrow(() -> new FlashCardSetNotFoundException("Không tồn tại bộ flash card có id là " + flashCardSetEditRequest.getId() + "của user " + user.getId()));
        FlashCardSet flashCardSet = flashCardSetMapper.toFlashCardSet(flashCardSetEditRequest, user);
        return flashCardSetRepository.save(flashCardSet);
    }

    @PreAuthorize("authentication.principal.user.id == #user.id")
    @Transactional
    public long delete(User user, Long id) {
        return flashCardSetRepository.deleteByIdAndUserId(id, user.getId());
    }

    @PostAuthorize("authentication.principal.user.id == returnObject.user.id or returnObject.isPublic")
    public FlashCardSet getFlashCardForPlay(Long id) {
        FlashCardSet flashCardSet = flashCardSetRepository.findById(id).orElseThrow(() -> new FlashCardSetNotFoundException("Không tồn tại bộ flash card có mã " + id));
        Collections.shuffle(flashCardSet.getFlashCards());
        return flashCardSet;
    }

    public Page<FlashCardSet> getAllPublicFlashCardSets(int pageNumber, int pageSize, String sortBy) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy));
        return flashCardSetRepository.findByIsPublicTrue(pageable);
    }

    public Page<FlashCardSet> getPublicFlashCardSetsByNameContaining(String searchKey, int pageNumber, int pageSize, String sortBy) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy));
        return flashCardSetRepository.findByIsPublicTrueAndNameContaining(searchKey, pageable);
    }
}
