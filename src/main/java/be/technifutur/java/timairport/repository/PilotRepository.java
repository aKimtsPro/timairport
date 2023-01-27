package be.technifutur.java.timairport.repository;

import be.technifutur.java.timairport.model.entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PilotRepository extends JpaRepository<Pilot, Long> {

    List<Pilot> findByFirstNameStartingWith(String start);

    @Query("SELECT p FROM Pilot p WHERE p.firstName = p.lastName")
    List<Pilot> findWithSameName();

}
