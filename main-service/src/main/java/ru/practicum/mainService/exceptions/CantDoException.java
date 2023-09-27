package ru.practicum.mainService.exceptions;

public class CantDoException extends RuntimeException {

    public CantDoException(String message) {
        super(message);
    }

}