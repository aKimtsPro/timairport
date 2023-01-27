package be.technifutur.java.timairport.model.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlaneInsertForm {

    @NotNull
    @Pattern(regexp = "[A-Z]-[A-Z]{4}|[A-Z]{2}-[A-Z]{3}|N[0-9]{1,5}[A-Z]{0,2}")
    private String callSign;
    @NotNull
    @PastOrPresent
    private LocalDate registrationDate;
    @NotNull
    private Long companyId;
    @NotNull
    private Long typeId;

}
