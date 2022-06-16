package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
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

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final InMemoryFilmStorage inMemoryFilmStorage;

    private final FilmService filmService;

    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<String> addFilm(@Valid @RequestBody Film film) {
        inMemoryFilmStorage.addFilm(film);
        return ResponseEntity.ok(film.getId() + " Film has been created");
    }

    @GetMapping
    public ResponseEntity<String> getAllFilms() {
        inMemoryFilmStorage.getAllFilms();
        return ResponseEntity.ok("All films found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getFilmById(@PathVariable long id) {
        inMemoryFilmStorage.getFilmById(id);
        return ResponseEntity.ok("Film found");
    }

    @PutMapping
    public ResponseEntity<String> updateFilm(@Valid @RequestBody Film film) {
        inMemoryFilmStorage.updateFilm(film);
        return ResponseEntity.ok(film.getId() + " Film has been updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable long id) {
        inMemoryFilmStorage.deleteFilm(id);
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
    public ResponseEntity<String> getPopularFilms(@RequestParam int count) {
        filmService.getPopularFilms(count);
        return ResponseEntity.ok("All popular films found");
    }
}
