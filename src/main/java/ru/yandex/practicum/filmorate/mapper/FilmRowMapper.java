package ru.yandex.practicum.filmorate.mapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.GenreDbStorage;
import ru.yandex.practicum.filmorate.repository.LikesDbStorage;
import ru.yandex.practicum.filmorate.repository.MpaDbStorage;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@Data
@RequiredArgsConstructor
public class FilmRowMapper implements RowMapper<Film> {

    private final GenreDbStorage genreDbStorage;
    private final MpaDbStorage mpaDbStorage;

    private final LikesDbStorage likesDbStorage;

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("ID"));
        film.setName(rs.getString("NAME"));
        film.setGenre(genreDbStorage.getGenreById(rs.getLong("GENRE")));
        film.setMpa(mpaDbStorage.getMpaById(rs.getLong("MPA")));
        film.setDescription(rs.getString("DESCRIPTION"));
        film.setDuration(rs.getInt("DURATION"));
        film.setReleaseDate(rs.getDate("RELEASEDATE").toLocalDate());
        film.setLikes(likesDbStorage.getLikesCount(rs.getLong("ID")));
        return film;
    }
}
