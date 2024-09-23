package com.example.CabBookingPortalSumit.service;


import com.example.CabBookingPortalSumit.entities.CabHistory;
import com.example.CabBookingPortalSumit.repository.BookingHistoryRepository;
import com.example.CabBookingPortalSumit.repository.CabHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AnalyticsServiceTest {

    @Mock
    private CabHistoryRepository cabHistoryRepository;

    @Mock
    private BookingHistoryRepository bookingHistoryRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCabHistory() {
        Long cabId = 1L;
        List<CabHistory> cabHistoryList = new ArrayList<>();
        when(cabHistoryRepository.findByCabId(cabId)).thenReturn(cabHistoryList);

        List<CabHistory> result = analyticsService.getCabHistory(cabId);

        assertEquals(cabHistoryList, result);
        verify(cabHistoryRepository, times(1)).findByCabId(cabId);
    }

    @Test
    void testGetMostDemandingCity() {
        List<Object[]> demandingCities = new ArrayList<>();
        when(bookingHistoryRepository.findMostDemandingCity()).thenReturn(demandingCities);

        List<Object[]> result = analyticsService.getMostDemandingCity();

        assertEquals(demandingCities, result);
        verify(bookingHistoryRepository, times(1)).findMostDemandingCity();
    }

    @Test
    void testGetMostPeakTime() {
        List<Object[]> peakTimes = new ArrayList<>();
        when(bookingHistoryRepository.findPeakDemandTime()).thenReturn(peakTimes);

        List<Object[]> result = analyticsService.getMostPeakTime();

        assertEquals(peakTimes, result);
        verify(bookingHistoryRepository, times(1)).findPeakDemandTime();
    }

    @Test
    void testGetBookingHistory() {
        Optional<Object[]> bookingHistoryList = Optional.empty();
        when(bookingHistoryRepository.getBookingHistory(LocalDateTime.MIN, LocalDateTime.MAX)).thenReturn(bookingHistoryList);

        Optional<Object[]> result = analyticsService.getBookingHistory(LocalDateTime.MIN, LocalDateTime.MAX);

        assertEquals(bookingHistoryList, result);
        verify(bookingHistoryRepository, times(1)).getBookingHistory(LocalDateTime.MIN, LocalDateTime.MAX);
    }
}

