package com.robotdreams.hotelbooking.service;

import com.robotdreams.hotelbooking.domain.Room;
import com.robotdreams.hotelbooking.dto.RoomDto;
import com.robotdreams.hotelbooking.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomDto> getAvailableRooms(String startDate, String endDate) {
        return roomRepository.getAvailableRooms(startDate, endDate).
                stream().map(RoomService::buildRoomDto).
                collect(Collectors.toList());
    }

    Optional<Room> findById(int id) {
        return roomRepository.findById(id);
    }

    private static RoomDto buildRoomDto(Room room) {
        return RoomDto.builder().id(room.getId()).
               roomNumber(room.getRoomNumber()).
               type(String.valueOf(room.getType())).build();
    }

    public void save(Room room) {
        roomRepository.save(room);
    }

    public boolean validateDates(String from, String to) {
        try {
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

            sdformat.setLenient(false);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            Date current = sdformat.parse(dtf.format(now));
            Date d1 = sdformat.parse(from);
            Date d2 = sdformat.parse(to);

            if(current.compareTo(d1) > 0) {
                return false;
            }

            if(d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
                return false;
            }

        } catch (ParseException exception) {
            return false;
        }

        return true;
    }

    public Optional<Room> findByRoomNumber(int number) {
        return roomRepository.findByRoomNumber(number);
    }

    @Transactional
    public int deleteRoomById(int id) {
        return roomRepository.deleteRoomById(id);
    }
}
