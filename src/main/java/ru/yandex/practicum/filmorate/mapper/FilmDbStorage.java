package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

public class FilmDbStorage implements FilmStorage {
    @Override
    public Film addFilm(Film film) {
        return null;
    }

    @Override
    public List<Film> getAllFilms() {
        return null;
    }

    @Override
    public Film getFilmById(long id) throws FilmNotFound {
        return null;
    }

    @Override
    public Film updateFilm(Film film) throws FilmNotFound {
        return null;
    }

    @Override
    public void deleteFilm(long filmId) throws FilmNotFound {

    }
}
