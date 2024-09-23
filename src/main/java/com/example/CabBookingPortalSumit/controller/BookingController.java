package com.example.CabBookingPortalSumit.controller;

import com.example.CabBookingPortalSumit.entities.BookingHistory;
import com.example.CabBookingPortalSumit.entities.Cab;
import com.example.CabBookingPortalSumit.enums.CabState;
import com.example.CabBookingPortalSumit.service.BookingService;
import com.example.CabBookingPortalSumit.service.CabService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final CabService cabService;

    @PostMapping("/book")
    public ResponseEntity<BookingHistory> bookCab(@RequestParam Long cityId) throws Exception {
        Optional<Cab> cab = cabService.findMostIdleCab(cityId);

        if (cab.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cab assignedCab = cab.get();

        BookingHistory booking = bookingService.bookCab(assignedCab, cityId);

        assignedCab.setState(CabState.ON_TRIP);
        assignedCab.setCityId(null);
        cabService.updateCab(assignedCab);


        return ResponseEntity.ok(booking);
    }
}
