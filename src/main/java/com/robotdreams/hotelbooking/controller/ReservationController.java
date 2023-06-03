package com.robotdreams.hotelbooking.controller;

import com.robotdreams.hotelbooking.domain.Guest;
import com.robotdreams.hotelbooking.domain.Reservation;
import com.robotdreams.hotelbooking.dto.ReservationRequestDto;
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
public class ReservationController {

    private final RoomService roomService;

    private final ReservationsService reservationsService;

    private final GuestService guestService;

    private static final String NON_AVAILABLE_ROOM = "room is not available for this date. " +
                                    "Please check your input data. Try change date or room id";

    private static final String INVALID_DATES_MESSAGE = "Invalid dates have been provided";


    @PostMapping("api/add/reservation")
    public ResponseEntity<Object> createReservation(@RequestBody ReservationRequestDto payload) {

        if (!roomService.validateDates(payload.getFromDate(), payload.getToDate())) {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("status", "error");
            hashMap.put("message", INVALID_DATES_MESSAGE);
            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }

        List<RoomDto> rooms = roomService.getAvailableRooms(payload.getFromDate(), payload.getToDate());

        boolean roomIsAvailable = false;
        for( RoomDto room: rooms ) {
            if((room.getId() == Integer.parseInt(payload.getRoomId()))) {
                roomIsAvailable = true;
            }
        }

        if(!roomIsAvailable) {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("status", "error");
            hashMap.put("message", NON_AVAILABLE_ROOM);

            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }

        List<Guest> guests = new ArrayList<>();
        Optional<Guest> guest = guestService.findGuestByEmail(payload.getEmail());

        if(guest.isEmpty()) {
            Guest createGuest = Guest.builder().
                    email(payload.getEmail()).
                    dob(payload.getDob()).
                    firstName(payload.getFirstName()).
                    lastName(payload.getLastName()).
                    passport(payload.getPassport()).build();
            guests.add(createGuest);
        } else {
            guests.add(guest.get());
        }

        try {
            Reservation reservation = Reservation.builder().
                    startDate(payload.getFromDate()).
                    endDate(payload.getToDate()).
                    roomId(Integer.valueOf(payload.getRoomId())).
                    active(true).
                    guest(guests).build();

            reservationsService.save(reservation);

        } catch (Exception e) {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("status", "error");
            hashMap.put("message", e.getMessage());
            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("api/reservations")
    public List<Reservation> getAllReservation() {

        List<Reservation> reservations = reservationsService.getAll();

        for (Reservation reservation : reservations) {
            List<Guest> guest = reservation.getGuest();
            System.out.println(guest.get(0).getEmail());
        }

        return reservationsService.getAll();


    }

    @GetMapping("api/guest/{id}/reservatinos")
    public List<Reservation> getGuestReservations(@PathVariable Long id) {
        return reservationsService.findReservationByGuest(id);
    }

    @DeleteMapping("api/reservation/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable int id) {
        int result = reservationsService.deleteById(id);
        System.out.println(result);
        if(result > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
