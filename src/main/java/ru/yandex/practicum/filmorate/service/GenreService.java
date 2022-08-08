package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreDbStorage;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GenreService {

    private final GenreDbStorage genreDbStorage;

    public Genre getGenreById(int id) {
        return genreDbStorage.getGenreById(id);
    }

    public List<Genre> getAllGenres() {
        return genreDbStorage.getAllGenres();
    }

}
