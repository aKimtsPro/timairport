package be.technifutur.java.timairport.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotSameAsValidator.class)
public @interface NotSameAsConstraint {

    String message() default "Fields values shouldn't match!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    NotSameAs[] constraints();
    Class<?> clazz();

}
