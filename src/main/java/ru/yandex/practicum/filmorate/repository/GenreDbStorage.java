package ru.yandex.practicum.filmorate.repository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Data
@Repository
@RequiredArgsConstructor
public class GenreDbStorage {

    public final JdbcTemplate jdbcTemplate;

    public Genre getGenreById(long id) throws UserNotFound {
        String sql = "SELECT * FROM GENRE WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new GenreRowMapper(), id);
    }

    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM GENRE";
        return jdbcTemplate.query(sql, new GenreRowMapper());
    }

}
