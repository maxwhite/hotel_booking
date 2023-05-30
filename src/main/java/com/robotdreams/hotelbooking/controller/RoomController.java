package com.robotdreams.hotelbooking.controller;

import com.robotdreams.hotelbooking.domain.Room;
import com.robotdreams.hotelbooking.dto.RoomDto;
import com.robotdreams.hotelbooking.repository.RoomRepository;
import com.robotdreams.hotelbooking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomRepository roomRepository;
    private final RoomService roomService;

    @PostMapping("api/room/add")
    public ResponseEntity<Object> addRoom(@RequestBody Room room) {

        try {
            roomRepository.save(room);
        }catch (Exception exception) {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("status", "error");
            hashMap.put("message", exception.getMessage());
            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("api/rooms/{from}/{to}")
    public ResponseEntity<Object> getRoomsAvailable(@PathVariable String from, @PathVariable String to) {

        if (!roomService.validateDates(from, to)) {
            return ResponseEntity.notFound().build();
        }

        try {
            List<RoomDto> roomDtos = roomService.getAvailableRooms(from, to);
            if(!roomDtos.isEmpty()) {
                return ResponseEntity.ok(roomDtos);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception exception) {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("status", "error");
            hashMap.put("message", exception.getMessage());
            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("api/room/update")
    public ResponseEntity<Object> updateRoom(@RequestBody Room payload) {
        try {
            roomService.save(payload);
            return ResponseEntity.ok().build();
        }catch (Exception exception) {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("status", "error");
            hashMap.put("message", exception.getMessage());
            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("api/room/number/{number}")
    public ResponseEntity<Room> findRoomByNumber(@PathVariable int number) {
        return roomService.findByRoomNumber(number).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("api/room/delete/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable int id) {
        if(roomService.deleteRoomById(id) > 0) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
