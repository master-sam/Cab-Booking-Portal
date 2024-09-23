package com.example.CabBookingPortalSumit.repository;

import com.example.CabBookingPortalSumit.entities.CabHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CabHistoryRepository extends JpaRepository<CabHistory, Long> {

    List<CabHistory> findByCabId(Long cabId);


    @Query(value = "SELECT SUM(TIMESTAMPDIFF(MINUTE, state_change_time, LEAD(state_change_time) OVER (PARTITION BY cab_id ORDER BY state_change_time)))" +
            "FROM cab_history " +
            "WHERE cab_id = :cabId AND state = 'IDLE' AND state_change_time BETWEEN :startTime AND :endTime;", nativeQuery = true)
    Optional<Long> findIdleDurationByCab(@Param("cabId") Long cabId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}

