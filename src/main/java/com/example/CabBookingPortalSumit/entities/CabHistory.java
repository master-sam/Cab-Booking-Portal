package com.example.CabBookingPortalSumit.entities;

import com.example.CabBookingPortalSumit.enums.CabState;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CabHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cityId;

    private Long cabId;

    @Enumerated(EnumType.STRING)
    private CabState state;

    private LocalDateTime stateChangeTime;
}
