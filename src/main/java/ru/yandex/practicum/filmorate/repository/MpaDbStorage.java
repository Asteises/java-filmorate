package ru.yandex.practicum.filmorate.repository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Data
public class MpaDbStorage {

    public final JdbcTemplate jdbcTemplate;

    public Mpa getMpaById(int id) throws UserNotFound {
        String sql = "SELECT * FROM MPA WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new MpaRowMapper(), id);
    }

    public List<Mpa> getAllMpa() {
        String sql = "SELECT * FROM MPA";
        return jdbcTemplate.query(sql, new MpaRowMapper());
    }

}
