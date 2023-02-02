package be.technifutur.java.timairport.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class InPastValidator implements ConstraintValidator<InPast, LocalDate> {

    private InPast constraint;

    @Override
    public void initialize(InPast constraintAnnotation) {
        constraint = constraintAnnotation;
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if( !value.plus( constraint.value(), constraint.unit() ).isBefore( LocalDate.now() ) ){
            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(
                    String.format("Should minimum %s %s in the past",
                            constraint.value(),
                            constraint.unit().toString().toLowerCase())
            ).addConstraintViolation();

            return false;
        }

        return true;
    }
}
