package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    /**
     * Добавляем новый Film
     */
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        filmService.addFilm(film);
        return film;
    }

    /**
     * Получаем всех Film
     */
    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    /**
     * Получаем Film по id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable long id) throws FilmNotFound {
        return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
    }

    /**
     * Изменяем существующий Film
     */
    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) throws FilmNotFound {
        return ResponseEntity.ok(filmService.updateFilm(film));
    }

    /**
     * Удаляем Film по id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable long id) throws FilmNotFound {
        filmService.deleteFilm(id);
        return ResponseEntity.ok("Film delete");
    }

    /**
     * Получаем Film по популярности(количеству like)
     */
    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopularFilms(@RequestParam(defaultValue = "0") int count) {
        return ResponseEntity.ok(filmService.getPopularFilms());
    }

}
