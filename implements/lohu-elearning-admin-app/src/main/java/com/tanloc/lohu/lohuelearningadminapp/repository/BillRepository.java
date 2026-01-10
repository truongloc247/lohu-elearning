package com.tanloc.lohu.lohuelearningadminapp.repository;

import com.tanloc.lohu.lohuelearningadminapp.dto.MonthlyRevenue;
import com.tanloc.lohu.lohuelearningadminapp.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Page<Bill> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("""
        SELECT NEW com.tanloc.lohu.lohuelearningadminapp.dto.MonthlyRevenue(
                   MONTH(b.creationDate),
                   SUM(b.amount)
               )
        FROM Bill b
        WHERE YEAR(b.creationDate) = :year AND b.isPurchased = true
        GROUP BY MONTH(b.creationDate)
        ORDER BY MONTH(b.creationDate) ASC
""")
    List<MonthlyRevenue> getMonthlyRevenue(@Param("year") int year);
}
