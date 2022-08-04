package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreDbStorage genreDbStorage;
    private final MpaDbStorage mpaDbStorage;
    private final LikesDbStorage likesDbStorage;

    @Override
    public Film addFilm(Film film) throws FilmNotFound {
        String sql = "INSERT INTO FILMS (" +
                "NAME, " +
                "MPA_ID, " +
                "DESCRIPTION, " +
                "RELEASE_DATE, " +
                "DURATION) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"ID"});
            ps.setString(1, film.getName());
            ps.setInt(2, film.getMpa().getId());
            ps.setString(3, film.getDescription());
            LocalDate realeaseDate = film.getReleaseDate();
            if (realeaseDate == null) {
                ps.setNull(4, Types.DATE);
            } else {
                ps.setDate(4, Date.valueOf(realeaseDate));
            }
            ps.setInt(5, film.getDuration());
            return ps;

        }, keyHolder);
        film.setId(keyHolder.getKey().longValue());
        setFilmGenre(film.getId(), film.getGenres());
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "SELECT * FROM FILMS";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Film.class));
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
                "MPA_ID = ?, " +
                "DESCRIPTION = ?, " +
                "RELEASE_DATE = ?, " +
                "DURATION = ? " +
                "WHERE ID = ?";
        jdbcTemplate.update(sql,
                film.getName(),
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

    public List<Film> getPopularFilms(int count) {
        String sqll = "SELECT *, COUNT(*) as total FROM FILMS f LEFT JOIN LIKES l on f.id = l.film_id GROUP BY f.id ORDER BY total DESC";
        if (count == 0) {
            return jdbcTemplate.query(sqll, new FilmRowMapper(genreDbStorage, mpaDbStorage, likesDbStorage))
                    .stream().limit(count).collect(Collectors.toList());
        } else {
            return jdbcTemplate.query(sqll, new FilmRowMapper(genreDbStorage, mpaDbStorage, likesDbStorage));
        }
    }

    public void setFilmGenre(long filmId, List<Genre> genres) throws FilmNotFound {
        String sqlCheck = "SELECT COUNT(*) FROM FILMS_GENRES WHERE FILM_ID = ?";
        Integer check = jdbcTemplate.queryForObject(sqlCheck, Integer.class, filmId);
        if (check == 0) {
            for (Genre genre : genres) {
                String sqlInsert = "INSERT INTO FILMS_GENRES (FILM_ID, GENRE_ID) VALUES (?, ?)";
                jdbcTemplate.update(sqlInsert, filmId, genre.getId());
            }
        } else {
            String sqlDelete = "DELETE FROM FILMS_GENRES WHERE FILM_ID = ?";
            jdbcTemplate.update(sqlDelete, filmId);
            for (Genre genre : genres) {
                String sqlMerge = "INSERT INTO FILMS_GENRES (FILM_ID, GENRE_ID) VALUES (?, ?)";
                jdbcTemplate.update(sqlMerge, filmId, genre.getId());
            }
        }
    }

    public List<Genre> getFilmGenres(long filmId) {
        String sqlSelect = "SELECT * FROM FILMS_GENRES WHERE FILM_ID = ?";
        return jdbcTemplate.query(sqlSelect, new GenreRowMapper(), filmId);
    }

}