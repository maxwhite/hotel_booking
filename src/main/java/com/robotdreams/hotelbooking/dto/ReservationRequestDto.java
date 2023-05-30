package com.robotdreams.hotelbooking.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReservationRequestDto {
    private String email;
    private String firstName;
    private String lastName;
    private String dob;
    private String roomId;
    private String fromDate;
    private String toDate;
    private String passport;
}
