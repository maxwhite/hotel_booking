package com.robotdreams.hotelbooking.repository;

import com.robotdreams.hotelbooking.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByEmail(String string);

    List<Guest> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Guest> findByPassport(String passport);

    Optional<Guest> findById(int id);

}
