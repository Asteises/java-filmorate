package ru.yandex.practicum.filmorate.repository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

@Repository
@Data
@RequiredArgsConstructor
public class LikesDbStorage {

    public final JdbcTemplate jdbcTemplate;

    public Integer getLikesCount(long id) throws UserNotFound {
        String sql = "SELECT COUNT(*) FROM LIKES WHERE FILM_ID = ?";
        Integer likes = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return likes;
    }

}
