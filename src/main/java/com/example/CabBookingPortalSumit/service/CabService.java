package com.example.CabBookingPortalSumit.service;

import com.example.CabBookingPortalSumit.entities.Cab;
import com.example.CabBookingPortalSumit.enums.CabState;
import com.example.CabBookingPortalSumit.repository.CabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CabService {
    private final CabRepository cabRepository;
    private final AnalyticsService analyticsService;

    public void registerCab(Cab cab) throws Exception {
        Optional<Cab> existingCab = cabRepository.findById(cab.getCabId());
        if (existingCab.isPresent()) {
            throw new Error("Cab with cabId " + cab.getCabId() + " is already registered.");
        }
        cab.setState(CabState.IDLE);
        cab.setLastIdleTime(LocalDateTime.now());
        analyticsService.setCabHistory(cab);
        cabRepository.save(cab);
    }

    public void updateCab(Cab cab) throws Exception  {
        Optional<Cab> existingCab = cabRepository.findById(cab.getCabId());
        if (existingCab.isEmpty()) {
            throw new Error("Cab with cabId " + cab.getCabId() + " is not registered, register cab first.");
        }
        analyticsService.setCabHistory(cab);
        cabRepository.save(cab);
    }

    public List<Cab> listAvailableCabs(Long cityId){
       return cabRepository.findByCityIdAndState(cityId, CabState.IDLE);
    }

    public Optional<Cab> findMostIdleCab(Long cityId){
        return cabRepository.findMostIdleCab(cityId, CabState.IDLE.name());
    }


}

