package be.technifutur.java.timairport.model.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class DemoForm {

    @NotNull
    private String name;

    @PositiveOrZero
    private int age;

}
