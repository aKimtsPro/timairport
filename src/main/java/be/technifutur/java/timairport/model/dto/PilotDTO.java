package be.technifutur.java.timairport.model.dto;

import be.technifutur.java.timairport.model.entity.Pilot;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PilotDTO {

    private Long id;
    private String name;

    public static PilotDTO from(Pilot entity){
        if( entity == null )
            return null;

        return PilotDTO.builder()
                .id( entity.getId() )
                .name( entity.getFirstName() + ' ' + entity.getLastName() )
                .build();
    }

}
