package be.technifutur.java.timairport.service.impl;

import be.technifutur.java.timairport.exceptions.FormValidationException;
import be.technifutur.java.timairport.exceptions.NoAvailablePlaneException;
import be.technifutur.java.timairport.exceptions.ReferrencedResourceNotFoundException;
import be.technifutur.java.timairport.model.dto.FlightDTO;
import be.technifutur.java.timairport.model.entity.Airport;
import be.technifutur.java.timairport.model.entity.Flight;
import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.form.FlightCreateForm;
import be.technifutur.java.timairport.repository.AirportRepository;
import be.technifutur.java.timairport.repository.FlightRepository;
import be.technifutur.java.timairport.repository.PilotRepository;
import be.technifutur.java.timairport.repository.PlaneRepository;
import be.technifutur.java.timairport.service.FlightService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;
    private final AirportRepository airportRepository;
    private final PilotRepository pilotRepository;

    public FlightServiceImpl(FlightRepository flightRepository, PlaneRepository planeRepository, AirportRepository airportRepository, PilotRepository pilotRepository) {
        this.flightRepository = flightRepository;
        this.planeRepository = planeRepository;
        this.airportRepository = airportRepository;
        this.pilotRepository = pilotRepository;
    }

    @Override
    public FlightDTO createFlight(FlightCreateForm form) {

        // validations simples
        form.validate();

        // mapping
        Flight flight = form.toEntity();
        flight.setDeparture(
                airportRepository.findById(form.getDepartureAirportId())
                        .orElseThrow( ReferrencedResourceNotFoundException::new )
        );
        flight.setDestination(
                airportRepository.findById(form.getArrivalAirportId())
                        .orElseThrow( ReferrencedResourceNotFoundException::new )
        );

        flight.setCaptain(
                pilotRepository.findById(form.getCaptainId())
                        .orElseThrow( ReferrencedResourceNotFoundException::new )
        );
        flight.setFirstOfficer(
                pilotRepository.findById(form.getFirstOfficerId())
                        .orElseThrow( ReferrencedResourceNotFoundException::new )
        );

        // Les pilotes doivent être de la bonne company
        if(
                !Objects.equals(flight.getFirstOfficer().getCompany().getId(), form.getCompanyId())
                || !Objects.equals(flight.getCaptain().getCompany().getId(), form.getCompanyId())
        )
            throw new FormValidationException("pilots should be from the same company as the plane");

        // Les airports doivent être compatible au type d'avion
        if(
                flight.getDeparture().getPlaneTypesAllowed().stream().noneMatch(type -> type.getId().equals(form.getTypePlaneId()) ) ||
                flight.getDestination().getPlaneTypesAllowed().stream().noneMatch(type -> type.getId().equals(form.getTypePlaneId()) )
        )
            throw new FormValidationException("airport should be compatible with the desired plane type");

        // Récupération des avions disponibles
        List<Plane> availablePlane = planeRepository.findAvailablePlane(
                form.getCompanyId(),
                form.getTypePlaneId(),
                form.getDateDeparture(),
                form.getDateArrival()
        );
//        List<Plane> availablePlane = planeRepository.findByCompany_IdAndType_IdAndInMaintenanceFalse(form.getCompanyId(), form.getTypePlaneId()).stream()
//                // On filtre les avions dispo
//                .filter( plane -> plane.getFlights().stream()
//                        .allMatch(f -> TimeTools.checkNoConflict(
//                                TimeRange.of( f.getDepartureTime(), f.getArrivalTime() ),
//                                TimeRange.of( form.getDateDeparture(), form.getDateArrival() )
//                        ))
//                )
//                .toList();
        if( availablePlane.isEmpty() )
            throw new NoAvailablePlaneException();


        // get pertinent plane
        Plane planeForFlight = availablePlane.stream()
                .filter(plane ->{
                        Optional<Airport> lastDestination = plane.getFlights().stream()
                                .filter(f -> f.getArrivalTime().isBefore(flight.getDepartureTime()))
                                .max(Comparator.comparing(Flight::getArrivalTime))
                                .map(Flight::getDestination);

                        return lastDestination.isPresent() && lastDestination.get().equals(flight.getDeparture());
                })
                .findAny()
                .orElseGet( ()-> availablePlane.get( new Random().nextInt(availablePlane.size()) ) );

        flight.setPlane( planeForFlight );
        Flight entity = flightRepository.save(flight);

        return FlightDTO.from( entity );
    }
}
