package com.tanloc.lohu.lohuelearninguserapp.repository;

import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCardSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public interface FlashCardSetRepository extends JpaRepository<FlashCardSet, Long> {
    Page<FlashCardSet> findByUserId(Long userId, Pageable pageable);

    Page<FlashCardSet> findByUserIdAndNameContaining(Long userId, String name, Pageable pageable);

    Optional<FlashCardSet> findByIdAndUserId(Long id, Long userId);

    long deleteByIdAndUserId(Long id, Long userId);
}
