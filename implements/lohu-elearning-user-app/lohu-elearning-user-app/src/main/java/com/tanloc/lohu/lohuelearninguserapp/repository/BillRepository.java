package com.tanloc.lohu.lohuelearninguserapp.repository;

import com.tanloc.lohu.lohuelearninguserapp.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
}
