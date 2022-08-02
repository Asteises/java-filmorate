package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmDbStorage;
import ru.yandex.practicum.filmorate.repository.LikesDbStorage;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {

    private final FilmDbStorage filmDbStorage;
    private final LikesDbStorage likesDbStorage;

    public void addFilm(Film film) {
        filmDbStorage.addFilm(film);
    }

    public List<Film> getAllFilms() {
        return filmDbStorage.getAllFilms();
    }

    public Film getFilmById(long filmId) throws FilmNotFound {
        return filmDbStorage.getFilmById(filmId);
    }

    public Film updateFilm(Film film) throws FilmNotFound {
        return filmDbStorage.updateFilm(film);
    }

    public void deleteFilm(long filmId) throws FilmNotFound {
        filmDbStorage.deleteFilm(filmId);
    }

    public List<Film> getPopularFilms() {
        return filmDbStorage.getPopularFilms();
    }
}
