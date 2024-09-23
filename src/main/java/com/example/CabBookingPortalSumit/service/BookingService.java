package com.example.CabBookingPortalSumit.service;

import com.example.CabBookingPortalSumit.entities.BookingHistory;
import com.example.CabBookingPortalSumit.entities.Cab;
import com.example.CabBookingPortalSumit.repository.BookingHistoryRepository;
import com.example.CabBookingPortalSumit.repository.CabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final CabRepository cabRepository;
    private final BookingHistoryRepository bookingHistoryRepository;

    public BookingHistory bookCab(Cab cab, Long cityId) {
        BookingHistory bookingHistory = new BookingHistory();
        bookingHistory.setCabId(cab.getCabId());
        bookingHistory.setCityId(cityId);
        bookingHistory.setBookingTime(LocalDateTime.now());

        return bookingHistoryRepository.save(bookingHistory);
    }
}

