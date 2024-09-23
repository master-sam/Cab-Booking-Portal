package com.example.CabBookingPortalSumit.entities;


import com.example.CabBookingPortalSumit.enums.CabState;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Cab {

    @Id
    private Long cabId;

    private Long cityId;

    @Enumerated(EnumType.STRING)
    private CabState state;

    private LocalDateTime lastIdleTime;
}
