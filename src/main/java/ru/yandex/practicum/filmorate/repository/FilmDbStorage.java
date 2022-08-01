package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreDbStorage genreDbStorage;
    private final MpaDbStorage mpaDbStorage;
    private final LikesDbStorage likesDbStorage;

    @Override
    public Film addFilm(Film film) {
        String sql = "INSERT INTO FILMS (NAME, GENRE, MPA, DESCRIPTION, RELEASEDATE, DURATION) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlId = "SELECT MAX(ID) FROM FILMS";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getGenre() != null ? film.getGenre().getId() : null,
                film.getMpa().getId(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration()
        );
        film.setId(jdbcTemplate.queryForObject(sqlId, Long.class));
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "SELECT * FROM FILMS";
        return jdbcTemplate.query(sql, new FilmRowMapper(genreDbStorage, mpaDbStorage, likesDbStorage));
    }

    @Override
    public Film getFilmById(long id) throws FilmNotFound {
        try {
            String sql = "SELECT * FROM FILMS WHERE ID = ?";
            return jdbcTemplate.queryForObject(sql, new FilmRowMapper(genreDbStorage, mpaDbStorage, likesDbStorage), id);
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFound("");
        }
    }

    @Override
    public Film updateFilm(Film film) throws FilmNotFound {
        getFilmById(film.getId());
        String sql = "UPDATE FILMS SET " +
                "NAME = ?, " +
                "GENRE = ?, " +
                "MPA = ?, " +
                "DESCRIPTION = ?, " +
                "RELEASEDATE = ?, " +
                "DURATION = ? " +
                "WHERE ID = ?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getGenre() != null ? film.getGenre().getId() : null,
                film.getMpa().getId(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId()
        );
        return film;
    }

    @Override
    public void deleteFilm(long filmId) throws FilmNotFound {
        String sql = "DELETE FROM FILMS WHERE ID = ?";
        jdbcTemplate.update(sql, filmId);
    }

    public List<Film> getPopularFilms() {
        String sql = "SELECT * FROM FILMS WHERE ID IN (SELECT FILM_ID, COUNT(USER_ID) FROM LIKES GROUP BY FILM_ID)";
        return jdbcTemplate.query(sql, new FilmRowMapper(genreDbStorage, mpaDbStorage, likesDbStorage));
    }
}
