package be.technifutur.java.timairport.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotSameAsValidator.class)
@Repeatable(NotSameAsConstraint.List.class)
public @interface NotSameAsConstraint {

    String message() default "Fields values shouldn't match!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> clazz();

    String field1();
    String field2();

    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotSameAsConstraint[] value();
    }

}
