package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private List<Film> films = new ArrayList<>();

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        films.add(film);
        return film;
    }

    @GetMapping
    public List<Film> getAll() {
        return films;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        for (Film f: films) {
            if (f.getId() == film.getId()) {
                f.setName(film.getName());
                f.setDescription(film.getDescription());
                f.setReleaseDate(film.getReleaseDate());
                f.setDuration(film.getDuration());
                return f;
            }
        }
        log.info("Film not found: {}", film.getId());
        return null;
    }
}
