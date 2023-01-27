package be.technifutur.java.timairport.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "origin_country", nullable = false)
    private String originCountry;

    @OneToMany(mappedBy = "company")
    private List<Plane> planes;

    @OneToMany(mappedBy = "company")
    private List<Pilot> pilots;

}
