package be.technifutur.java.timairport.repository;

import be.technifutur.java.timairport.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
