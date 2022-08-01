package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    FilmDto addFilm(FilmDto filmDto);

    List<Film> getAllFilms();

    Film getFilmById(long id) throws FilmNotFound;

    FilmDto updateFilm(FilmDto filmDto) throws FilmNotFound;

    void deleteFilm(long filmId) throws FilmNotFound;
}
