package be.technifutur.java.timairport.controller;

import be.technifutur.java.timairport.model.dto.FlightDTO;
import be.technifutur.java.timairport.model.form.FlightCreateForm;
import be.technifutur.java.timairport.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public FlightDTO createFlight(@RequestBody @Valid FlightCreateForm form){
        return flightService.createFlight(form);
    }
}
