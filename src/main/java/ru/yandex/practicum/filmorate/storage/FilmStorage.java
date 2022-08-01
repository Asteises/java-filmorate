package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film Film);

    List<Film> getAllFilms();

    Film getFilmById(long id) throws FilmNotFound;

    Film updateFilm(Film film) throws FilmNotFound;

    void deleteFilm(long filmId) throws FilmNotFound;
}
