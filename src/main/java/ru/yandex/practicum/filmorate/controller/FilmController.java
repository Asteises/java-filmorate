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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private Map<Integer, Film> films;
    private int id = 1;
    public FilmController() {
        this.films = new HashMap();
    }

    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        film.setId(id++);
        films.put(film.getId(), film);
        log.info("Film has been created: {}", film);
        return film;
    }

    @GetMapping
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {

        if (films.get(film.getId()) != null) {
            films.put(film.getId(), film);
            log.info("Film has been updated: {}", film);
            return film;
        }

        log.info("Film not found: {}", film.getId());
        throw new RuntimeException("Film не найден");
    }
}
