package com.example.CabBookingPortalSumit.controller;

import com.example.CabBookingPortalSumit.entities.Cab;
import com.example.CabBookingPortalSumit.enums.CabState;
import com.example.CabBookingPortalSumit.service.AnalyticsService;
import com.example.CabBookingPortalSumit.service.CabService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CabControllerTest {

    @Mock
    private CabService cabService;

    @Mock
    private AnalyticsService analyticsService;

    @InjectMocks
    private CabController cabController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cabController).build();
    }

    @Test
    void testRegisterCab_Success() throws Exception {
        Cab cab = new Cab();
        cab.setCabId(1L);
        cab.setState(CabState.IDLE);

        cabService.registerCab(any(Cab.class));

        mockMvc.perform(post("/cab/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cabId\": 1, \"state\": \"IDLE\"}"))
                .andExpect(status().isOk());

        verify(cabService, times(1)).registerCab(any(Cab.class));
    }

    @Test
    void testUpdateCab_Success() throws Exception {
        Cab cab = new Cab();
        cab.setCabId(1L);
        cab.setState(CabState.ON_TRIP);

        cabService.updateCab(any(Cab.class));

        mockMvc.perform(post("/cab/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cabId\": 1, \"state\": \"ON_TRIP\"}"))
                .andExpect(status().isOk());

        verify(cabService, times(1)).updateCab(any(Cab.class));
    }

    @Test
    void testFindAvailableCab_Success() throws Exception {
        Long cityId = 100L;
        List<Cab> availableCabs = new ArrayList<>();
        Cab cab1 = new Cab();
        cab1.setCabId(1L);
        cab1.setCityId(cityId);
        cab1.setState(CabState.IDLE);
        availableCabs.add(cab1);

        when(cabService.listAvailableCabs(cityId)).thenReturn(availableCabs);

        mockMvc.perform(get("/cab/" + cityId + "/available")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cabService, times(1)).listAvailableCabs(cityId);
    }

    @Test
    void testFindAvailableCab_NoContent() throws Exception {
        Long cityId = 100L;

        when(cabService.listAvailableCabs(cityId)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/cab/" + cityId + "/available")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(cabService, times(1)).listAvailableCabs(cityId);
    }
}
