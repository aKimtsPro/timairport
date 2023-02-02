package be.technifutur.java.timairport.repository;

import be.technifutur.java.timairport.model.entity.Company;
import be.technifutur.java.timairport.model.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface PlaneRepository extends JpaRepository<Plane, Long> {

    @Modifying
    @Transactional // utiles dans le cas d'un UPDATE/DELETE
    @Query("UPDATE Plane p SET p.inMaintenance = ?2 WHERE p.id = ?1")
    void updateMaintenance(long id, boolean maintenance);

    @Modifying
    @Transactional // utiles dans le cas d'un UPDATE/DELETE
    @Query("UPDATE Plane p SET p.company = ?2 WHERE p.id = ?1")
    void updateCompany(long id, Company company);

    List<Plane> findByCompany_IdAndType_IdAndInMaintenanceFalse(Long id, Long type);

    @Query("""
        SELECT p
        FROM Plane p
        WHERE
            p.company.id = ?1 AND
            p.type.id = ?2 AND
            NOT p.inMaintenance
            AND (
                SELECT count(f)
                FROM Flight f
                WHERE
                    p.id = f.plane.id AND
                    NOT ( f.arrivalTime < ?3 OR f.departureTime > ?4)
            ) = 0
    """)
    List<Plane> findAvailablePlane(Long companyId, Long typeId, LocalDateTime departure, LocalDateTime arrival);


}