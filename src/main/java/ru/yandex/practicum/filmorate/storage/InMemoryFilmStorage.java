package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films;
    private long id = 1;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
    }

    @Override
    public FilmDto addFilm(FilmDto film) {
        film.setId(id++);
//        films.put(film.getId(), film);
        log.info("Film has been created: {}", film);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    public Film getFilmById(long filmId) throws FilmNotFound {
        if (films.containsKey(filmId)) {
            return films.get(filmId);
        }
        log.info("Film not found: {}", filmId);
        throw new FilmNotFound("Film не найден");
    }

    @Override
    public Film updateFilm(Film film) throws FilmNotFound {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Film has been updated: {}", film);
            return film;
        }
        log.info("Film not found: {}", film.getId());
        throw new FilmNotFound("Film не найден");
    }

    @Override
    public void deleteFilm(long filmId) throws FilmNotFound {
        if (films.containsKey(filmId)) {
            films.remove(filmId);
            log.info("Film delete");
        }
        log.info("Film not found: {}", filmId);
        throw new FilmNotFound("Film не найден");
    }
}
