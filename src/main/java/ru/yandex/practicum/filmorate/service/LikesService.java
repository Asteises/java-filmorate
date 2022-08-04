package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.repository.LikesDbStorage;

@Slf4j
@Service
@AllArgsConstructor
public class LikesService {

    private final LikesDbStorage likesDbStorage;

    public void addLikeToFilm(long filmId, long userId) throws UserNotFound, FilmNotFound {
        likesDbStorage.addLike(filmId, userId);
        log.info("Like has been add");
    }

    public void deleteLikeFromFilm(long userId, long filmId) throws UserNotFound, FilmNotFound {
        likesDbStorage.deleteLikeFromFilm(userId, filmId);
        log.info("Like deleted");
    }

}
