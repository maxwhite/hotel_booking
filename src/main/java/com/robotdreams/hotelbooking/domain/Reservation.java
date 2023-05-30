package com.robotdreams.hotelbooking.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int roomId;

    @Column(nullable = false, columnDefinition = "DATE")
    private String startDate;

    @Column(nullable = false, columnDefinition = "DATE")
    private String endDate;

    @ManyToMany(cascade = { CascadeType.DETACH })
    private List<Guest> guest;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean active;
}
