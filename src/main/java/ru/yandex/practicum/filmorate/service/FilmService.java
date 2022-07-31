package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.filmStorage = inMemoryFilmStorage;
        this.userStorage = inMemoryUserStorage;
    }

    public void addFilm(Film film) {
//        filmStorage.addFilm(film);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(long filmId) throws FilmNotFound {
        return filmStorage.getFilmById(filmId);
    }

    public void updateFilm(Film film) throws FilmNotFound {
        filmStorage.updateFilm(film);
    }

    public void deleteFilm(long filmId) throws FilmNotFound {
        filmStorage.deleteFilm(filmId);
    }

    public void addLikeToFilm(long filmId, long userId) throws UserNotFound, FilmNotFound {
//        filmStorage.getFilmById(filmId).getLikes().add(userId);
//        log.info("Like has been add");
    }

    public void deleteLikeFromFilm(long filmId, long userId) throws UserNotFound, FilmNotFound {
//        if (filmStorage.getAllFilms().contains(filmStorage.getFilmById(filmId)) &&
//                userStorage.getAllUsers().contains(userStorage.getUserById(userId))) {
//            filmStorage.getFilmById(filmId).getLikes().remove(userId);
//            log.info("Like has been deleted");
//            return;
//        }
//        log.info("User or Film not found: {} or {}", userId, filmId);
//        throw new RuntimeException("User или Film не найден");
    }

    public List<Film> getPopularFilms(int count) {
//        if (count == 0) count = 10;
//        List<Film> films = filmStorage.getAllFilms().stream()
//                .sorted(Comparator.comparingInt(film -> film.getLikes().size()))
//                .collect(Collectors.toList());
//        Collections.reverse(films);
//        return films.stream().limit(count).collect(Collectors.toList());
        return null;
    }
}
