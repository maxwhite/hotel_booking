package com.robotdreams.hotelbooking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomDto {
    private Long id;
    private String type;
    private int roomNumber;
}