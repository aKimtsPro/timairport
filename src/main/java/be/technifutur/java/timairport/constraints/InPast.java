package be.technifutur.java.timairport.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Target( ElementType.FIELD )
@Retention( RetentionPolicy.RUNTIME )
@Constraint( validatedBy = {
        InPastValidator.class
} )
public @interface InPast {

    String message() default "Should be 7 days in the past";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value() default 7;
    ChronoUnit unit() default ChronoUnit.DAYS;

}
