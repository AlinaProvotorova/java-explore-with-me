package ru.practicum.stats.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {


    @ExceptionHandler({
            IllegalStateException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBadRequestException(final RuntimeException e) {
        log.error("Bad request: {}", e.getMessage(), e);
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage defaultHandlerExceptionResolver(final MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        String errorMessage = Objects.requireNonNull(
                result.getFieldError()).getField() + " " + result.getFieldError().getDefaultMessage();
        log.error("Validation error: {}", errorMessage, e);
        return new ErrorMessage(errorMessage);
    }
}
