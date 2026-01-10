package com.tanloc.lohu.lohuelearningadminapp.repository;

import com.tanloc.lohu.lohuelearningadminapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
        SELECT u from User u WHERE u.accountType = true AND u.vipExpirationDate <= :today
    """)
    List<User> findExpiredPremiumUsers(@Param("today") LocalDateTime today);
}
