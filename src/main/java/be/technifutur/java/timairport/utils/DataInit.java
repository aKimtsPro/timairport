package be.technifutur.java.timairport.utils;

import be.technifutur.java.timairport.model.entity.Airport;
import be.technifutur.java.timairport.model.entity.Company;
import be.technifutur.java.timairport.model.entity.Pilot;
import be.technifutur.java.timairport.model.entity.TypePlane;
import be.technifutur.java.timairport.repository.AirportRepository;
import be.technifutur.java.timairport.repository.CompanyRepository;
import be.technifutur.java.timairport.repository.PilotRepository;
import be.technifutur.java.timairport.repository.TypePlaneRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInit implements InitializingBean {

    private final CompanyRepository companyRepository;
    private final TypePlaneRepository typePlaneRepository;
    private final PilotRepository pilotRepository;
    private final AirportRepository airportRepository;

    public DataInit(
            CompanyRepository companyRepository,
            TypePlaneRepository typePlaneRepository,
            PilotRepository pilotRepository,
            AirportRepository airportRepository) {
        this.companyRepository = companyRepository;
        this.typePlaneRepository = typePlaneRepository;
        this.pilotRepository = pilotRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        TypePlane type1 = new TypePlane();
        type1.setName("big_plane");
        type1.setCapacity(300);

        type1 = typePlaneRepository.save( type1 );

        TypePlane type2 = new TypePlane();
        type2.setName("average_plane");
        type2.setCapacity(200);

        type2 = typePlaneRepository.save( type2 );

        TypePlane type3 = new TypePlane();
        type3.setName("small_plane");
        type3.setCapacity(100);

        type3 = typePlaneRepository.save( type3 );

        List<TypePlane> types = List.of(type1,type2, type3);


        Company company1 = new Company();
        company1.setName("big money company");
        company1.setOriginCountry("USA");

        company1 = companyRepository.save( company1 );

        Company company2 = new Company();
        company2.setName("Deedlamerd");
        company2.setOriginCountry("Belgium");

        company2 = companyRepository.save( company2 );


        Pilot pilot1 = new Pilot();

        pilot1.setCompany(company1);
        pilot1.setLicenseId("L001");
        pilot1.setFirstName("Louis");
        pilot1.setLastName("Labrancote");
        pilot1.setEmail("l.l@pilote.com");
        pilot1.setPhone("0400/00.00.00");
        pilot1.setAddress("rue de Louis 4");
        pilot1.setLicenseAcquisition(LocalDate.EPOCH);

        pilot1 = pilotRepository.save( pilot1 );

        Pilot pilot2 = new Pilot();

        pilot2.setCompany(company1);
        pilot2.setLicenseId("L002");
        pilot2.setFirstName("Louise");
        pilot2.setLastName("Michel");
        pilot2.setEmail("le.l@pilote.com");
        pilot2.setPhone("0401/00.00.00");
        pilot2.setAddress("rue de Louise 3");
        pilot2.setLicenseAcquisition(LocalDate.EPOCH);

        pilot2 = pilotRepository.save( pilot2 );

        Airport airport1 = new Airport();
        airport1.setName("Aéroport de Bxl");
        airport1.setCity("Bruxelles");
        airport1.setCountry("Belgique");
        airport1.setAddress("rue de l'aéroport");
        airport1.setPlaneParking(5);
        airport1.setPlaneTypesAllowed( types );

        airport1 = airportRepository.save(airport1);

        Airport airport2 = new Airport();
        airport2.setName("Aéroport de Paris");
        airport2.setCity("Paris");
        airport2.setCountry("France");
        airport2.setAddress("rue de l'aéroport");
        airport2.setPlaneParking(5);
        airport2.setPlaneTypesAllowed( types );

        airport2 = airportRepository.save(airport2);


    }
}
