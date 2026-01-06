package com.tanloc.lohu.lohuelearninguserapp.repository;

import com.tanloc.lohu.lohuelearninguserapp.entity.LearningFolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

@Controller
public interface LearningFolderRepository extends JpaRepository<LearningFolder, Long> {
    Page<LearningFolder> findByUserId(Long userId, Pageable pageable);

    Page<LearningFolder> findByUserIdAndNameContaining(Long userId, String name, Pageable pageable);
}
