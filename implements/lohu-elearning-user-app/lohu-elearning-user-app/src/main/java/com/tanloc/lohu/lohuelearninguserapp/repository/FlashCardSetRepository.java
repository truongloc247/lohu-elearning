package com.tanloc.lohu.lohuelearninguserapp.repository;

import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCardSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashCardSetRepository extends JpaRepository<FlashCardSet, Long> {
    Page<FlashCardSet> findByUserIdAndNameContaining(Long userId, String name, Pageable pageable);

    Page<FlashCardSet> findByUserId(Long userId, Pageable pageable);
}
