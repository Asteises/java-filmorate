package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRawMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Review review = new Review();
        review.setReviewId(rs.getLong("REVIEW_ID"));
        review.setPositive(rs.getBoolean("IS_POSITIVE"));
        review.setContent(rs.getString("CONTENT"));
        review.setFilmId(rs.getLong("FILM_ID"));
        review.setUserId(rs.getLong("USER_ID"));
        review.setUseful(rs.getInt("USEFUL"));
        return review;
    }

}
