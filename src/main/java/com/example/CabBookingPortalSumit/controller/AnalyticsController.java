package com.example.CabBookingPortalSumit.controller;

import com.example.CabBookingPortalSumit.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/demand")
    public ResponseEntity<?> getMostDemandingCity() {
        return ResponseEntity.ok(analyticsService.getMostDemandingCity());
    }

    @GetMapping("/peakTime")
    public ResponseEntity<?> getMostPeakTime() {
        return ResponseEntity.ok(analyticsService.getMostPeakTime());
    }

    @GetMapping("/{cabId}")
    public ResponseEntity<?> getCabHistory(@PathVariable Long cabId) {
        return ResponseEntity.ok(analyticsService.getCabHistory(cabId));
    }

    @GetMapping("/cabIdleTime")
    public ResponseEntity<?> getCabHistory(@RequestParam Long cabId, @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        return ResponseEntity.ok(analyticsService.getIdleTimeByCab(cabId, startTime, endTime));
    }

    @GetMapping("/bookingHistory")
    public ResponseEntity<?> getBookingHistory(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        return ResponseEntity.ok(analyticsService.getBookingHistory(startTime, endTime));
    }
}
