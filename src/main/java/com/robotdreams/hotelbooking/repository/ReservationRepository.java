package com.robotdreams.hotelbooking.repository;

import com.robotdreams.hotelbooking.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value =
           "SELECT * FROM reservation join reservation_guest on reservation.id = reservation_guest.reservation_id " +
           "join guest on guest.id = reservation_guest.guest_id WHERE guest.id = :guestId;" , nativeQuery = true)
    List<Reservation> findReservationByGuest(@Param("guestId") String guestId);

    int deleteById(int id);
}
