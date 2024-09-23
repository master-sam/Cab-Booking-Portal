package com.example.CabBookingPortalSumit.controller;

import com.example.CabBookingPortalSumit.entities.CabHistory;
import org.junit.jupiter.api.BeforeEach;
import com.example.CabBookingPortalSumit.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AnalyticsController.class)
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyticsService analyticsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMostDemandingCity() throws Exception {
        List<Object[]> demandingCities = new ArrayList<>();
        when(analyticsService.getMostDemandingCity()).thenReturn(demandingCities);

        mockMvc.perform(get("/analytics/demand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetMostPeakTime() throws Exception {
        List<Object[]> peakTimes = new ArrayList<>();
        when(analyticsService.getMostPeakTime()).thenReturn(peakTimes);

        mockMvc.perform(get("/analytics/peakTime"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetCabIdleTime() throws Exception {
        Long cabId = 1L;
        List<CabHistory> cabHistory = new ArrayList<>();
        when(analyticsService.getCabHistory(cabId)).thenReturn(cabHistory);

        mockMvc.perform(get("/analytics/{cabId}", cabId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}

