package ru.yandex.practicum.filmorate.repository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

@Repository
@RequiredArgsConstructor
@Data
public class MpaDbStorage {

    public final JdbcTemplate jdbcTemplate;

    public Mpa getMpaById(long id) throws UserNotFound {
        String sql = "SELECT * FROM MPA WHERE ID = ?";
        Mpa mpa = jdbcTemplate.queryForObject(sql, new MpaRowMapper(), id);
        return mpa;
    }

}
