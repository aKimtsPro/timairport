package be.technifutur.java.timairport.service;

import be.technifutur.java.timairport.model.dto.FlightDTO;
import be.technifutur.java.timairport.model.form.FlightCreateForm;

public interface FlightService {

    FlightDTO createFlight(FlightCreateForm form);

}
