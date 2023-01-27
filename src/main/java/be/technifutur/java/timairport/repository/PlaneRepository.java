package be.technifutur.java.timairport.repository;

import be.technifutur.java.timairport.model.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
}