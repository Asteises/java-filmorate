package ru.yandex.practicum.filmorate.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({UserNotFound.class, FilmNotFound.class})
    public ResponseEntity<String> runtimeHandler(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }
}
