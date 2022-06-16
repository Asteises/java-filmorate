package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Comparator;
import java.util.List;

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
        }
        log.info("User or Film not found: {} or {}", userId, filmId);
        throw new RuntimeException("User или Film не найден");
    }

    public void deleteLikeFromFilm(long filmId, long userId) {
        inMemoryFilmStorage.getFilmById(filmId).getLikes().remove(userId);
    }

    public List<Film> getPopularFilms(int count) {
        return null;
    }

    public static final Comparator<Film> COMPARE_BY_COUNT = new Comparator<Film>() {

        @Override
        public int compare(Film f1, Film f2) {
            return f1.getLikes().size() - f2.getLikes().size();
        }
    };
}
