package com.example.CabBookingPortalSumit.service;


import com.example.CabBookingPortalSumit.entities.Cab;
import com.example.CabBookingPortalSumit.entities.CabHistory;
import com.example.CabBookingPortalSumit.repository.BookingHistoryRepository;
import com.example.CabBookingPortalSumit.repository.CabHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final CabHistoryRepository cabHistoryRepository;
    private final BookingHistoryRepository bookingHistoryRepository;

    public List<CabHistory> getCabHistory(Long cabId) {
        return cabHistoryRepository.findByCabId(cabId);
    }

    public void setCabHistory(Cab cab) {
        CabHistory cabHistory = new CabHistory();
        cabHistory.setCabId(cab.getCabId());
        cabHistory.setState(cab.getState());
        cabHistory.setCityId(cab.getCityId());
        cabHistory.setStateChangeTime(LocalDateTime.now());
        cabHistoryRepository.save(cabHistory);
    }


    public List<Object[]> getMostDemandingCity() {
        return bookingHistoryRepository.findMostDemandingCity();
    }

    public List<Object[]> getMostPeakTime() {
        return bookingHistoryRepository.findPeakDemandTime();
    }

    public Optional<Long> getIdleTimeByCab(Long cabId, LocalDateTime startTime, LocalDateTime endTime) {
        return cabHistoryRepository.findIdleDurationByCab(cabId, startTime, endTime);
    }

    public Optional<Object[]> getBookingHistory(LocalDateTime startTime, LocalDateTime endTime) {
        return bookingHistoryRepository.getBookingHistory(startTime, endTime);
    }
}


