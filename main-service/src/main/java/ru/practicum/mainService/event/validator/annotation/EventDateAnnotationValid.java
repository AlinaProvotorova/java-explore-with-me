package ru.practicum.mainService.event.validator.annotation;

import ru.practicum.mainService.event.validator.EventDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(EventDatesAnnotationValid.class)
@Constraint(validatedBy = EventDateValidator.class)
public @interface EventDateAnnotationValid {
    String message() default "EventDate must be at least 2 hours later than the current time and cannot be null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean isAdmin() default false;
}