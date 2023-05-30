package com.robotdreams.hotelbooking.repository;

import com.robotdreams.hotelbooking.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value =
    "select * from room WHERE id NOT IN (select reservation.room_id from reservation \n" +
    "WHERE ((:startDate >= `start_date` AND :startDate < `end_date`) OR (:endDate > `start_date` AND :endDate <= `end_date`)\n" +
    "OR (:startDate <= `start_date` AND :endDate > `start_date`)  OR (:startDate < `end_date` AND :endDate <= `end_date`)) AND `active` = 1);" , nativeQuery = true)
    List<Room> getAvailableRooms(@Param("startDate") String startDate, @Param("endDate") String endDate);

    Optional<Room> findById(int id);

    Optional<Room> findByRoomNumber(int number);

    int deleteRoomById(int id);
}
