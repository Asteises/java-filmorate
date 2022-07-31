package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
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
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmDbStorage;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    private final FilmDbStorage filmDbStorage;

    @PostMapping
    public FilmDto addFilm(@Valid @RequestBody FilmDto filmDto) {
        filmDbStorage.addFilm(filmDto);
        return filmDto;
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable long id) throws FilmNotFound {
        return new ResponseEntity<>(filmDbStorage.getFilmById(id), HttpStatus.OK);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws FilmNotFound {
        filmService.updateFilm(film);
        return film;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable long id) throws FilmNotFound {
        filmService.deleteFilm(id);
        return ResponseEntity.ok("Film delete");
    }

    @PutMapping("{id}/like/{userId}")
    public ResponseEntity<String> addLikeToFilm(@PathVariable long id, @PathVariable long userId) throws UserNotFound, FilmNotFound {
        filmService.addLikeToFilm(id, userId);
        return ResponseEntity.ok("Like add");
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<String> deleteLikeFromFilm(@PathVariable long id, @PathVariable long userId) throws UserNotFound, FilmNotFound {
        filmService.deleteLikeFromFilm(id, userId);
        return ResponseEntity.ok("Like delete");
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopularFilms(@RequestParam(defaultValue = "0") int count) {
        return ResponseEntity.ok(filmService.getPopularFilms(count));
    }
}
