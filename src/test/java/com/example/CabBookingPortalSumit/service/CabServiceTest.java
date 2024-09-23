package com.example.CabBookingPortalSumit.service;


import com.example.CabBookingPortalSumit.entities.Cab;
import com.example.CabBookingPortalSumit.enums.CabState;
import com.example.CabBookingPortalSumit.repository.CabRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CabServiceTest {

    @Mock
    private CabRepository cabRepository;

    @Mock
    private AnalyticsService analyticsService;

    @InjectMocks
    private CabService cabService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCab_Success() throws Exception {
        // Arrange
        Cab cab = new Cab();
        cab.setCabId(1L);
        cab.setState(CabState.IDLE);
        cab.setLastIdleTime(LocalDateTime.now());

        when(cabRepository.findById(cab.getCabId())).thenReturn(Optional.empty());

        // Act
        cabService.registerCab(cab);

        // Assert
        verify(cabRepository, times(1)).save(cab);
        verify(analyticsService, times(1)).setCabHistory(cab);
    }

    @Test
    void testRegisterCab_CabAlreadyRegistered() {
        // Arrange
        Cab cab = new Cab();
        cab.setCabId(1L);

        when(cabRepository.findById(cab.getCabId())).thenReturn(Optional.of(cab));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> cabService.registerCab(cab));
        assertEquals("Cab with cabId 1 is already registered.", exception.getMessage());

        verify(cabRepository, never()).save(any(Cab.class));
        verify(analyticsService, never()).setCabHistory(any(Cab.class));
    }

    @Test
    void testUpdateCab_Success() throws Exception {
        // Arrange
        Cab cab = new Cab();
        cab.setCabId(1L);

        when(cabRepository.findById(cab.getCabId())).thenReturn(Optional.of(cab));

        // Act
        cabService.updateCab(cab);

        // Assert
        verify(cabRepository, times(1)).save(cab);
    }

    @Test
    void testUpdateCab_CabNotRegistered() {
        // Arrange
        Cab cab = new Cab();
        cab.setCabId(1L);

        when(cabRepository.findById(cab.getCabId())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> cabService.updateCab(cab));
        assertEquals("Cab with cabId 1 is not registered, register cab first.", exception.getMessage());

        verify(cabRepository, never()).save(any(Cab.class));
    }

    @Test
    void testListAvailableCabs() {
        // Arrange
        Long cityId = 1L;
        List<Cab> cabs = List.of(new Cab(), new Cab());

        when(cabRepository.findByCityIdAndState(cityId, CabState.IDLE)).thenReturn(cabs);

        // Act
        List<Cab> availableCabs = cabService.listAvailableCabs(cityId);

        // Assert
        assertEquals(2, availableCabs.size());
        verify(cabRepository, times(1)).findByCityIdAndState(cityId, CabState.IDLE);
    }

    @Test
    void testFindMostIdleCab() {
        // Arrange
        Long cityId = 1L;
        Cab cab = new Cab();
        cab.setCabId(1L);

        when(cabRepository.findMostIdleCab(cityId, CabState.IDLE.name())).thenReturn(Optional.of(cab));

        // Act
        Optional<Cab> result = cabService.findMostIdleCab(cityId);

        // Assert
        assertEquals(1L, result.get().getCabId());
        verify(cabRepository, times(1)).findMostIdleCab(cityId, CabState.IDLE.name());
    }
}

