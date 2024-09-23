package com.example.CabBookingPortalSumit.repository;

import com.example.CabBookingPortalSumit.entities.Cab;
import com.example.CabBookingPortalSumit.enums.CabState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CabRepository extends JpaRepository<Cab, Long> {

    List<Cab> findByCityIdAndState(Long cityId, CabState state);

    @Query(value = "SELECT c.* FROM cab c " +
            "WHERE c.city_id = :cityId " +
            "AND c.state = :state " +
            "ORDER BY TIMESTAMPDIFF(MINUTE, c.last_idle_time, NOW()) DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Cab> findMostIdleCab(@Param("cityId") Long cityId, @Param("state") String state);


}

