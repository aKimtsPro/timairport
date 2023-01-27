package be.technifutur.java.timairport.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;


// Permet de créer un repo "à la main"

//@Repository
public class CustomPilotRepository {

    @PersistenceContext
    private EntityManager manager;

}
