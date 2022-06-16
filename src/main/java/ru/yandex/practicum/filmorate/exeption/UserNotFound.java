package ru.yandex.practicum.filmorate.exeption;

public class UserNotFound extends Exception {
    public UserNotFound(String message) {
        super(message);
    }
}
