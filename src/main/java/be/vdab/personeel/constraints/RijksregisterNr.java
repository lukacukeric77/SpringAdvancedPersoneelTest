package be.vdab.personeel.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, ANNOTATION_TYPE, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = RijksregisterNrValidator.class)
public @interface RijksregisterNr {
    String message() default "{be.vdab.personeel.constraints.RijksregisterNr.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
