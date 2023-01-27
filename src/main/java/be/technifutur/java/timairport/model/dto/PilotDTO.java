package be.technifutur.java.timairport.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PilotDTO {

    private Long id;
    private String name;

}
