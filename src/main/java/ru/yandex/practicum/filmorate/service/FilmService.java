package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;

    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void addLikeToFilm(long filmId, long userId) {
        if (inMemoryFilmStorage.getFilms().containsKey(filmId) && inMemoryUserStorage.getUsers().containsKey(userId)) {
            inMemoryFilmStorage.getFilmById(filmId).getLikes().add(userId);
            log.info("Like has been add");
            return;
        }
        log.info("User or Film not found: {} or {}", userId, filmId);
        throw new RuntimeException("User или Film не найден");
    }

    public void deleteLikeFromFilm(long filmId, long userId) {
        if (inMemoryFilmStorage.getFilms().containsKey(filmId) && inMemoryUserStorage.getUsers().containsKey(userId)) {
            inMemoryFilmStorage.getFilmById(filmId).getLikes().remove(userId);
            log.info("Like has been deleted");
            return;
        }
        log.info("User or Film not found: {} or {}", userId, filmId);
        throw new RuntimeException("User или Film не найден");
    }

    public List<Film> getPopularFilms(int count) {
        if (count == 0) count = 10;
        return inMemoryFilmStorage.getAllFilms().stream()
                .sorted(Comparator.comparing(film -> film.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
