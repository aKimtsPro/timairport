package be.technifutur.java.timairport.model.dto;

import be.technifutur.java.timairport.model.entity.Airport;
import be.technifutur.java.timairport.model.entity.Flight;
import be.technifutur.java.timairport.model.entity.Pilot;
import be.technifutur.java.timairport.model.entity.Plane;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightDTO {

    private long id;
    private AirportDTO departure;
    private AirportDTO destination;
    private PilotDTO captain;
    private PilotDTO firstOfficer;
    private PlaneDTO plane;

    @Data
    @Builder
    private static class AirportDTO {
        private long id;
        private String name;
        private String location;

        public static AirportDTO from(Airport entity){
            if( entity == null )
                return null;

            return AirportDTO.builder()
                .id( entity.getId() )
                .name(entity.getName())
                .location( String.format("%s,\n%s, %s", entity.getAddress(), entity.getCity(), entity.getCountry()) )
                .build();
        }
    }

    @Data
    @Builder
    private static class PlaneDTO {
        private long id;
        private String callSign;
        private int capacity;

        public static PlaneDTO from(Plane entity){
            if( entity == null )
                return null;

            return PlaneDTO.builder()
                    .id( entity.getId() )
                    .callSign( entity.getCallSign() )
                    .capacity( entity.getType().getCapacity() )
                    .build();
        }
    }

    public static FlightDTO from(Flight entity){

        if( entity == null )
            return null;

        return FlightDTO.builder()
                .id( entity.getId() )
                .captain( PilotDTO.from( entity.getCaptain() ) )
                .firstOfficer( PilotDTO.from(entity.getFirstOfficer() ) )
                .departure( AirportDTO.from(entity.getDeparture() ) )
                .destination( AirportDTO.from(entity.getDestination()) )
                .plane( PlaneDTO.from( entity.getPlane() ) )
                .build();

    }


}
