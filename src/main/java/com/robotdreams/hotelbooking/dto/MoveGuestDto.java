package com.robotdreams.hotelbooking.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoveGuestDto {
    private String reservationId;
    private String roomId;
    private String dateFrom;
    private String dateTo;
}
