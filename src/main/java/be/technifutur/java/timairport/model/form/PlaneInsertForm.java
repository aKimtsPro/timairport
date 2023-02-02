package be.technifutur.java.timairport.model.form;

import be.technifutur.Not0;

import be.technifutur.java.timairport.constraints.InPast;
import be.technifutur.java.timairport.model.entity.Plane;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
public class PlaneInsertForm {

    @NotNull
    @Pattern(regexp = "[A-Z]-[A-Z]{4}|[A-Z]{2}-[A-Z]{3}|N[0-9]{1,5}[A-Z]{0,2}")
    private String callSign;
    @NotNull
//    @PastOrPresent
    @InPast(value = 3, unit = ChronoUnit.MONTHS)
    private LocalDate registrationDate;
    @NotNull
    @Not0
    private Long companyId;
    @NotNull
    private Long typeId;


    public Plane toEntity(){

        Plane plane = new Plane();

        plane.setCallSign( this.callSign );
        plane.setRegistrationDate( this.getRegistrationDate() );

        return plane;

    }

}
