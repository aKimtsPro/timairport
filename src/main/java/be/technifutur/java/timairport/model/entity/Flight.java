package be.technifutur.java.timairport.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id", nullable = false)
    private Long id;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departure;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id", nullable = false)
    private Airport destination;

    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false)
    private Plane plane;

    @ManyToOne
    @JoinColumn(name = "captain_id", nullable = false)
    private Pilot captain;
    @ManyToOne
    @JoinColumn(name = "first_officer_id", nullable = false)
    private Pilot firstOfficer;

    @Column(name = "cancelled", nullable = false)
    private boolean cancelled = false;

    @OneToMany(mappedBy = "flight")
    private List<Booking> bookings;

}
