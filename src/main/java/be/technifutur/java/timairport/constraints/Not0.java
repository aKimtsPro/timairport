package be.technifutur.java.timairport.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Not0Validator.class)
public @interface Not0 {
    String message() default "not 0!!!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
