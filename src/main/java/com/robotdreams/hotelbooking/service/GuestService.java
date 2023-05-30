package com.robotdreams.hotelbooking.service;

import com.robotdreams.hotelbooking.domain.Guest;
import com.robotdreams.hotelbooking.domain.Reservation;
import com.robotdreams.hotelbooking.domain.Room;
import com.robotdreams.hotelbooking.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    public Optional<Guest> findGuestByEmail(String email) {

        return guestRepository.findByEmail(email);
    }

    public List<Guest> findByFirstNameAndLastName(String firstName, String lastName) {

        return guestRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Optional<Guest> findByPassport(String passport) {

        return guestRepository.findByPassport(passport);
    }

    public void changeRoom(Reservation reservation, Room room) {

    }

    public Optional<Guest> findById(Long id) {
        return guestRepository.findById(id);
    }

    public List<Guest> findAll() {
        return guestRepository.findAll();
    }
}
