package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
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
    public FilmDto addFilm(FilmDto filmDto) {
        String sql = "INSERT INTO FILMS VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                filmDto.getId(),
                filmDto.getName(),
                filmDto.getGenre(),
                filmDto.getMpa(),
                filmDto.getDescription(),
                filmDto.getReleaseDate(),
                filmDto.getDuration()
        );
        return filmDto;
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "SELECT * FROM FILMS";
        return jdbcTemplate.query(sql, new FilmRowMapper(genreDbStorage, mpaDbStorage, likesDbStorage));
    }

    @Override
    public Film getFilmById(long id) throws FilmNotFound {
        String sql = "SELECT * FROM FILMS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new FilmRowMapper(genreDbStorage, mpaDbStorage, likesDbStorage), id);
    }

    @Override
    public FilmDto updateFilm(FilmDto filmDto) throws FilmNotFound {
        String sql = "UPDATE FILMS SET " +
                "NAME = ?, " +
                "GENRE = ?, " +
                "MPA = ?, " +
                "DESCRIPTION = ?, " +
                "RELEASEDATE = ?, " +
                "DURATION = ? " +
                "WHERE ID = ?";
        jdbcTemplate.update(sql,
                filmDto.getName(),
                filmDto.getGenre(),
                filmDto.getMpa(),
                filmDto.getDescription(),
                filmDto.getReleaseDate(),
                filmDto.getDuration(),
                filmDto.getId()
        );
        return filmDto;
    }

    @Override
    public void deleteFilm(long filmId) throws FilmNotFound {
        String sql = "DELETE FROM FILMS WHERE ID = ?";
        jdbcTemplate.update(sql, filmId);
    }
}
