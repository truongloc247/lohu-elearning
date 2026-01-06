package com.tanloc.lohu.lohuelearninguserapp.service;

import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCardSet;
import com.tanloc.lohu.lohuelearninguserapp.repository.FlashCardSetRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FlashCardSetService {
    FlashCardSetRepository flashCardSetRepository;

    public Page<FlashCardSet> getByUserId(Long userId, int pageNumber, int pageSize, String sortBy) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).ascending());
        return flashCardSetRepository.findByUserId(userId, pageable);
    }

    public Page<FlashCardSet> getByUserIdAndNameContaining(Long userId, String name, int pageNumber, int pageSize, String sortBy) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).ascending());
        return flashCardSetRepository.findByUserIdAndNameContaining(userId, name, pageable);
    }
}
