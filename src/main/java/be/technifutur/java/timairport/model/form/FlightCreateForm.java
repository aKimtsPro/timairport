package be.technifutur.java.timairport.model.form;

import be.technifutur.java.timairport.constraints.NotSameAsConstraint;
import be.technifutur.java.timairport.exceptions.FormValidationException;
import be.technifutur.java.timairport.model.entity.Flight;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NotSameAsConstraint(
        clazz = FlightCreateForm.class,
        field1 = "captainId",
        field2 = "firstOfficerId"
)
@NotSameAsConstraint(
        clazz = FlightCreateForm.class,
        field1 = "departureAirportId",
        field2 = "arrivalAirportId"
)
public class FlightCreateForm {

    @NotNull
    @Future
    private LocalDateTime dateDeparture;
    @NotNull
    @Future
    private LocalDateTime dateArrival;
    @NotNull
    private Long departureAirportId;
    @NotNull
    private Long arrivalAirportId;
    @NotNull
    private Long captainId;
    @NotNull
    private Long firstOfficerId;
    @NotNull
    private long typePlaneId;
    @NotNull
    private long companyId;

    public Flight toEntity(){

        Flight flight = new Flight();

        flight.setDepartureTime( dateDeparture );
        flight.setArrivalTime( dateArrival );

        return flight;
    }

    public void validate(){
        if( Objects.equals(this.getArrivalAirportId(), this.getDepartureAirportId()) )
            throw new FormValidationException("airport should not be the same");

        if( Objects.equals(this.getCaptainId(), this.getFirstOfficerId()) )
            throw new FormValidationException("pilotes should not be the same");

        if( Duration.between( this.getDateDeparture(), this.getDateArrival() ).toDays() >= 2 )
            throw new FormValidationException("travel time should be less than 2 days");

        if( this.getDateArrival().isBefore( this.getDateDeparture() ) )
            throw new FormValidationException("departure should be before arrival");
    }

}
