package be.technifutur.java.timairport.constraints;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NotSameAsValidator implements ConstraintValidator<NotSameAsConstraint, Object> {

    private NotSameAsConstraint annotation;
    private Class<?> annotated;

    @Override
    public void initialize(NotSameAsConstraint constraintAnnotation) {
        annotation = constraintAnnotation;
        annotated = constraintAnnotation.clazz();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        boolean valid = true;
        for (NotSameAs constraint : annotation.constraints()) {
            if( !validateConstraint(constraint, value, context) )
                valid = false;
        }
        return valid;

    }

    private boolean validateConstraint(NotSameAs constraint, Object value,  ConstraintValidatorContext context){

        String fieldName1 = constraint.field1();
        String fieldName2 = constraint.field2();

        try {
            Class<?> fieldType1 = annotated.getDeclaredField(fieldName1).getType();
            Class<?> fieldType2 = annotated.getDeclaredField(fieldName2).getType();
            Method getter1 = extractGetter(fieldName1, fieldType1);
            Method getter2 = extractGetter(fieldName2, fieldType2);
            Object fieldValue1 = getter1.invoke(value);
            Object fieldValue2 = getter2.invoke(value);

            if( fieldValue1.equals(fieldValue2) ){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        String.format("%s and %s shouldn't hold equal values", fieldName1, fieldName2)
                ).addConstraintViolation();
                return false;
            }
            else
                return true;

        } catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private Method extractGetter(String fieldName, Class<?> fieldType) throws NoSuchMethodException {

        String getterName;
        if(fieldType.equals(Boolean.class) || fieldType.equals(boolean.class))
            getterName = "is" + (char)(fieldName.charAt(0) + ('A'-'a') ) + fieldName.substring(1);
        else
            getterName = "get" + (char)(fieldName.charAt(0) + ('A'-'a') ) + fieldName.substring(1);

        return annotated.getMethod(getterName);

    }
}
