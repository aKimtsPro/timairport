package be.technifutur.java.timairport.repository;

import be.technifutur.java.timairport.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
