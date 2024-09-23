package com.example.CabBookingPortalSumit.controller;

import com.example.CabBookingPortalSumit.entities.BookingHistory;
import com.example.CabBookingPortalSumit.entities.Cab;
import com.example.CabBookingPortalSumit.enums.CabState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.CabBookingPortalSumit.service.BookingService;
import com.example.CabBookingPortalSumit.service.CabService;

import java.util.Optional;

import static org.mockito.Mockito.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private CabService cabService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void testBookCab_Success() throws Exception {
        Long cityId = 100L;
        Cab cab = new Cab();
        cab.setCabId(1L);
        cab.setState(CabState.IDLE);

        BookingHistory bookingHistory = new BookingHistory();
        bookingHistory.setCabId(cab.getCabId());
        bookingHistory.setCityId(cityId);

        when(cabService.findMostIdleCab(cityId)).thenReturn(Optional.of(cab));
        when(bookingService.bookCab(any(Cab.class), anyLong())).thenReturn(bookingHistory);

        mockMvc.perform(post("/booking/book")
                        .param("cityId", cityId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cabService, times(1)).findMostIdleCab(cityId);
        verify(bookingService, times(1)).bookCab(any(Cab.class), eq(cityId));
        verify(cabService, times(1)).updateCab(any(Cab.class));
    }

    @Test
    void testBookCab_CabNotFound() throws Exception {
        Long cityId = 100L;

        when(cabService.findMostIdleCab(cityId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/booking/book")
                        .param("cityId", cityId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(cabService, times(1)).findMostIdleCab(cityId);
        verify(bookingService, times(0)).bookCab(any(Cab.class), anyLong());
        verify(cabService, times(0)).updateCab(any(Cab.class));
    }
}

