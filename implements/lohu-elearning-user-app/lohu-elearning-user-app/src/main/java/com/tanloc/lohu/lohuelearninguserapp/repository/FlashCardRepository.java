package com.tanloc.lohu.lohuelearninguserapp.repository;

import com.tanloc.lohu.lohuelearninguserapp.entity.FlashCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    Page<FlashCard> findByFlashCardSet_IdAndFlashCardSet_User_Id(Long flashCardSetId, Long userId, Pageable pageable);

    @Query("""
                SELECT f FROM FlashCard f
                WHERE
                    f.flashCardSet.id = :flashCardSetId AND
                    f.flashCardSet.user.id = :userId AND
                    (f.term LIKE CONCAT('%', :searchKey, '%') OR f.definition LIKE CONCAT('%', :searchKey, '%'))
                    order by f.id asc
    """)
    Page<FlashCard> findByFlashCardSetIdAndUserIdAndSearchKey (@Param("flashCardSetId") Long flashCardSetId, @Param("userId") Long userId, @Param("searchKey") String searchKey, Pageable pageable);

    Optional<FlashCard> findByIdAndFlashCardSet_User_Id(Long id, Long userId);
}
