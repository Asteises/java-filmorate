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
        return null;
    }

    @Override
    public Film getFilmById(long id) throws FilmNotFound {
        String sql = "SELECT * FROM FILMS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new FilmRowMapper(genreDbStorage, mpaDbStorage, likesDbStorage), id);
    }

    @Override
    public Film updateFilm(Film film) throws FilmNotFound {
        return null;
    }

    @Override
    public void deleteFilm(long filmId) throws FilmNotFound {

    }
}
