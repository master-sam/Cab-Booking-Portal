package com.example.CabBookingPortalSumit.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class BookingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(nullable = false)
    private Long cabId;

    @Column(nullable = false)
    private Long cityId;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    private LocalDateTime endTime;
}

