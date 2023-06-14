package com.robotdreams.hotelbooking.service;

import com.robotdreams.hotelbooking.domain.Reservation;
import com.robotdreams.hotelbooking.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationsService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public void save(Reservation reservation) {

        reservationRepository.save(reservation);
    }

    public Optional<Reservation> findReservationById(Long id) {
       return reservationRepository.findById(id);
    }

    public List<Reservation> findReservationByGuest(Long id) {
        String guestId = id.toString();
        return reservationRepository.findReservationByGuest(guestId);
    }
    @Transactional
    public int deleteById(int id) {
        return reservationRepository.deleteById(id);
    }

    public List<Reservation> getReservationByRoomId(int id) {
        return reservationRepository.getReservationByRoomId(id);
    }
}
