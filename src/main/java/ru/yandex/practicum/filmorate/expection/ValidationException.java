package ru.yandex.practicum.filmorate.expection;

public class ValidationException extends Exception{

    public ValidationException(String message) {
        super(message);
    }
}
