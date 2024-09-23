package com.example.CabBookingPortalSumit.controller;


import com.example.CabBookingPortalSumit.entities.Cab;
import com.example.CabBookingPortalSumit.enums.CabState;
import com.example.CabBookingPortalSumit.service.AnalyticsService;
import com.example.CabBookingPortalSumit.service.CabService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cab")
@RequiredArgsConstructor
public class CabController {
    private final CabService cabService;
    private final AnalyticsService analyticsService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCab(@RequestBody Cab cab) {
        try {
            cabService.registerCab(cab);
            return new ResponseEntity<>(cab.getCabId()+" registered. ", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateCab(@RequestBody Cab cab) {
        try {
            cab.setState(CabState.IDLE);
            cab.setLastIdleTime(LocalDateTime.now());
            cabService.updateCab(cab);
            return new ResponseEntity<>(cab.getCabId()+" updated. ", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{cityId}/available")
    public ResponseEntity<List<Cab>> findAvailableCab(@PathVariable Long cityId) {
        List<Cab> availableCabs = cabService.listAvailableCabs(cityId);
        if (availableCabs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(availableCabs);
    }


}

