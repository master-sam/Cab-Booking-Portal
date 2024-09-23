package com.example.CabBookingPortalSumit.repository;

import com.example.CabBookingPortalSumit.entities.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {

    @Query("SELECT bh.cityId, COUNT(bh) as demandCount FROM BookingHistory bh GROUP BY bh.cityId ORDER BY demandCount DESC")
    List<Object[]> findMostDemandingCity();

    @Query("SELECT bh.cityId, HOUR(bh.bookingTime), COUNT(bh) as demandCount FROM BookingHistory bh GROUP BY bh.cityId, HOUR(bh.bookingTime) ORDER BY demandCount DESC")
    List<Object[]> findPeakDemandTime();

    @Query(value = "SELECT booking_id "+
            "FROM booking_history " +
            "WHERE booking_time BETWEEN :startTime AND :endTime;", nativeQuery = true)
    Optional<Object[]> getBookingHistory(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}


