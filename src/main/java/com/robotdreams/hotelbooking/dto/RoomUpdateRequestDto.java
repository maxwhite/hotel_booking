package com.robotdreams.hotelbooking.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoomUpdateRequestDto {
   private int id;
   private String roomType;
}
