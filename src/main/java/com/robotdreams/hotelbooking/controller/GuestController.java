package com.robotdreams.hotelbooking.controller;

import com.robotdreams.hotelbooking.domain.Guest;
import com.robotdreams.hotelbooking.domain.Reservation;
import com.robotdreams.hotelbooking.dto.MoveGuestDto;
import com.robotdreams.hotelbooking.dto.RoomDto;
import com.robotdreams.hotelbooking.service.GuestService;
import com.robotdreams.hotelbooking.service.ReservationsService;
import com.robotdreams.hotelbooking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;
    private final ReservationsService reservationsService;
    private final RoomService roomService;
    private final String RESERVATION_NOT_FOUND = "Reservation not found";
    private final String NO_AVAILABLE_ROOMS_FOR_THESE_DATES = "No available rooms for these dates";

    @GetMapping("api/guest/{firstName}/{lastName}")
    public ResponseEntity<List<Guest>> getGuestByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {

        List<Guest> result = guestService.findByFirstNameAndLastName(firstName, lastName);

        if(!result.isEmpty()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("api/guest/{passport}")
    public ResponseEntity<Guest> getGuestByPassport(@PathVariable String passport) {

      return guestService.findByPassport(passport).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("api/guest/move")
    public ResponseEntity<Object> changeRoom(@RequestBody MoveGuestDto payload) {

        Long reservationId = Long.parseLong(payload.getReservationId());
        Optional<Reservation> reservationContainer = reservationsService.findReservationById(reservationId);

        Reservation reservation;
        if(reservationContainer.isPresent()) {
            reservation = reservationContainer.get();

            List<RoomDto> rooms = roomService.getAvailableRooms(payload.getDateFrom(), payload.getDateTo());

            boolean roomIsAvailable = false;

            for( RoomDto room: rooms ) {
                if((room.getId() == Integer.parseInt(payload.getRoomId()))) {
                    roomIsAvailable = true;
                }
            }

            if(!roomIsAvailable) {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("status", "error");
                hashMap.put("message", NO_AVAILABLE_ROOMS_FOR_THESE_DATES);
                hashMap.put("start date", payload.getDateFrom());
                hashMap.put("end date", payload.getDateTo());
                hashMap.put("roomId", payload.getRoomId());

                return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
            }
        } else {

            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("status", "error");
            hashMap.put("message", RESERVATION_NOT_FOUND);

            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);

        }

        reservation.setActive(false);

        reservationsService.save(reservation);

        Long guestId = reservation.getGuest().get(0).getId();

        Optional<Guest> guestContainer = guestService.findById(guestId);
        Guest guest = guestContainer.get();
        List<Guest> guestList = new ArrayList<>();
        guestList.add(guest);

        Reservation newReservation = Reservation.builder().
                startDate(payload.getDateFrom()).
                endDate(payload.getDateTo()).
                roomId(Integer.valueOf(payload.getRoomId())).
                active(true).
                guest(guestList).build();

        reservationsService.save(newReservation);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("api/guests")
    public List<Guest> getAllGuests() {
        return guestService.findAll();
    }

}
