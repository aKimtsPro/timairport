package be.technifutur.java.timairport.controller;

import be.technifutur.java.timairport.model.dto.PilotDTO;
import be.technifutur.java.timairport.service.PilotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pilot")
public class PilotController {

    private final PilotService pilotService;

    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @GetMapping("/all")
    public List<PilotDTO> getAll(){
        return pilotService.getAll();
    }
}
