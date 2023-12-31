package ru.practicum.mainService.event.validator;


import ru.practicum.mainService.event.validator.annotation.EventDateAnnotationValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class EventDateValidator implements ConstraintValidator<EventDateAnnotationValid, LocalDateTime> {

    private boolean isAdmin;

    @Override
    public void initialize(EventDateAnnotationValid annotation) {
        isAdmin = annotation.isAdmin();
    }

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
        if (eventDate == null) {
            return true;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime minValidEventDate = isAdmin ? currentTime.plusHours(1) : currentTime.plusHours(2);

        return eventDate.isAfter(minValidEventDate);
    }
}

