package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films;

    public InMemoryFilmStorage(Map<Long, Film> films) {
        this.films = films;
    }

    @Override
    public Film addFilm(Film film) {
        films.put(film.getId(), film);
        log.info("Film has been created: {}", film);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    public Film getFilmById(long filmId) {
        if (films.containsKey(filmId)) {
            return films.get(filmId);
        }
        log.info("Film not found: {}", filmId);
        throw new RuntimeException("Film не найден");
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Film has been updated: {}", film);
            return film;
        }
        log.info("Film not found: {}", film.getId());
        throw new RuntimeException("Film не найден");
    }

    @Override
    public void deleteFilm(long filmId) {
        if (films.containsKey(filmId)) {
            films.remove(filmId);
            log.info("Film delete");
        }
        log.info("Film not found: {}", filmId);
        throw new RuntimeException("Film не найден");
    }
}
