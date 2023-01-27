package be.technifutur.java.timairport.mapper;

import be.technifutur.java.timairport.model.dto.PlaneDTO;
import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.form.PlaneInsertForm;
import org.springframework.stereotype.Service;

//@Service // Autre facon de mettre en place un mapper
public class PlaneMapper {

    public PlaneDTO toDto(Plane entity){

        if( entity == null )
            return null;

        return PlaneDTO.builder()
                .id( entity.getId() )
                .inMaintenance(entity.isInMaintenance() )
                .callSign( entity.getCallSign() )
                .registrationDate( entity.getRegistrationDate() )
                .company(
                        PlaneDTO.CompanyDTO.builder()
                                .id( entity.getCompany().getId() )
                                .name( entity.getCompany().getName() )
                                .originCountry( entity.getCompany().getOriginCountry() )
                                .build()
                )
                .type(
                        PlaneDTO.TypeDTO.builder()
                                .id( entity.getType().getId() )
                                .name( entity.getType().getName() )
                                .capacity( entity.getType().getCapacity() )
                                .build()
                )
                .build();

    }


    public Plane toEntity(PlaneInsertForm form){
        Plane entity = new Plane();

        entity.setCallSign( form.getCallSign() );
        entity.setRegistrationDate( form.getRegistrationDate() );

        return entity;
    }

}
