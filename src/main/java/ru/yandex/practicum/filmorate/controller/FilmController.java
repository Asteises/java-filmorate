package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<String> addFilm(@Valid @RequestBody Film film) {
        filmService.addFilm(film);
        return ResponseEntity.ok(film.getId() + " Film has been created");
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable long id) {
        return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateFilm(@Valid @RequestBody Film film) {
        filmService.updateFilm(film);
        return ResponseEntity.ok(film.getId() + " Film has been updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable long id) {
        filmService.deleteFilm(id);
        return ResponseEntity.ok("Film delete");
    }

    @PutMapping("{id}/like/{userId}")
    public ResponseEntity<String> addLikeToFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.addLikeToFilm(id, userId);
        return ResponseEntity.ok("Like add");
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<String> deleteLikeFromFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.deleteLikeFromFilm(id, userId);
        return ResponseEntity.ok("Like delete");
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopularFilms(@RequestParam int count) {
        return new ResponseEntity<>(filmService.getPopularFilms(count), HttpStatus.OK);
    }
}
