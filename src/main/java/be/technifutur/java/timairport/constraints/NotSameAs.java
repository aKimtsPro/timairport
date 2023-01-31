package be.technifutur.java.timairport.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface NotSameAs {

    String field1();
    String field2();

}