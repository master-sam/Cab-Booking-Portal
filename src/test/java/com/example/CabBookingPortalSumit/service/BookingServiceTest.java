package com.example.CabBookingPortalSumit.service;


import com.example.CabBookingPortalSumit.entities.BookingHistory;
import com.example.CabBookingPortalSumit.entities.Cab;
import com.example.CabBookingPortalSumit.repository.BookingHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingHistoryRepository bookingHistoryRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookCab() {
        // Arrange
        Cab cab = new Cab();
        cab.setCabId(1L);

        Long cityId = 100L;
        BookingHistory expectedBookingHistory = new BookingHistory();
        expectedBookingHistory.setCabId(cab.getCabId());
        expectedBookingHistory.setCityId(cityId);
        expectedBookingHistory.setBookingTime(LocalDateTime.now());

        when(bookingHistoryRepository.save(any(BookingHistory.class))).thenReturn(expectedBookingHistory);

        // Act
        BookingHistory result = bookingService.bookCab(cab, cityId);

        // Assert
        assertEquals(expectedBookingHistory.getCabId(), result.getCabId());
        assertEquals(expectedBookingHistory.getCityId(), result.getCityId());
        verify(bookingHistoryRepository, times(1)).save(any(BookingHistory.class));
    }
}

