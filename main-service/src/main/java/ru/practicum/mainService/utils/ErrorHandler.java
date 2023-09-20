package ru.practicum.mainService.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainService.exceptions.ErrorMessage;
import ru.practicum.mainService.exceptions.NotFoundException;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage notFoundException(final NotFoundException e) {
        log.error("Resource not found: {}", e.getMessage(), e);
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class
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