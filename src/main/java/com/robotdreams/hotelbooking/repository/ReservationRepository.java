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
           "SELECT * FROM reservation where id in (select reservation_id from reservation_guest where guest_id = :guestId)" , nativeQuery = true)
    List<Reservation> findReservationByGuest(@Param("guestId") String guestId);

    @Query(value =
            "SELECT * FROM reservation WHERE room_id = :roomId", nativeQuery = true)
    List <Reservation> getReservationByRoomId(@Param("roomId") int id);

    int deleteById(int id);
}
