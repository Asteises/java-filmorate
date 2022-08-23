package ru.yandex.practicum.filmorate.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.exeption.ReviewNotFound;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.mapper.ReviewRawMapper;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

import java.sql.PreparedStatement;
import java.util.List;

@Getter
@Setter
@Repository
@RequiredArgsConstructor
public class ReviewDbStorage implements ReviewStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmDbStorage filmDbStorage;
    private final UserService userService;

    public Boolean isReviewExist(long reviewId) {
        String sql = "SELECT COUNT(*) FROM REVIEWS WHERE REVIEW_ID = ?";
        int check = jdbcTemplate.queryForObject(sql, Integer.class, reviewId);
        if (check != 0) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Review addReview(Review review) {
        String sql = "INSERT INTO REVIEWS (USEFUL, IS_POSITIVE, CONTENT, USER_ID, FILM_ID) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"REVIEW_ID"});
            ps.setInt(1, review.getUseful());
            ps.setBoolean(2, review.isPositive());
            ps.setString(3, review.getContent());
            ps.setLong(4, review.getUserId());
            ps.setLong(5, review.getFilmId());
            return ps;
        }, keyHolder);
        review.setReviewId(keyHolder.getKey().longValue());
        return review;
    }

    @Override
    public Review updateReview(Review review) throws ReviewNotFound {
        if (isReviewExist(review.getReviewId())) {
            String sql = "UPDATE REVIEWS SET USEFUL = ?, " +
                    "IS_POSITIVE = ?, " +
                    "CONTENT = ?, " +
                    "USER_ID = ?, " +
                    "FILM_ID = ? " +
                    "WHERE REVIEW_ID = ?";
            jdbcTemplate.update(sql,
                    review.getUseful(),
                    review.isPositive(),
                    review.getContent(),
                    review.getUserId(),
                    review.getFilmId(),
                    review.getReviewId());
            return review;
        } else {
            throw new ReviewNotFound("");
        }
    }

    @Override
    public void deleteReview(long reviewId) throws ReviewNotFound {
        getReviewById(reviewId);
        String sql = "DELETE FROM REVIEWS WHERE REVIEW_ID = ?";
        jdbcTemplate.update(sql, reviewId);
    }

    @Override
    public Review getReviewById(long reviewId) throws ReviewNotFound {
        try {
            String sql = "SELECT * FROM REVIEWS WHERE REVIEW_ID = ?";
            return jdbcTemplate.queryForObject(sql, new ReviewRawMapper(), reviewId);
        } catch (EmptyResultDataAccessException e) {
            throw new ReviewNotFound("Review  данным id не найден: " + review.getReviewId());
        }
    }

    @Override
    public List<Review> getAllReviewByFilmId(long filmId, int count) throws FilmNotFound {
        filmDbStorage.getFilmById(filmId);
        if (filmDbStorage.getFilmById(filmId) != null) {
            String sql = "SELECT * FROM REVIEWS WHERE FILM_ID = ?";
            if (count != 0) {
                return jdbcTemplate.query(sql, new ReviewRawMapper(), filmId);
            } else {
                return jdbcTemplate.query(sql, new ReviewRawMapper(), filmId);
            }
        } else {
            String sql = "SELECT * FROM REVIEWS";
            if (count != 0) {
                return jdbcTemplate.query(sql, new ReviewRawMapper(), count);
            } else {
                return jdbcTemplate.query(sql, new ReviewRawMapper(),10);
            }
        }
    }

    @Override
    public Review addLikeReview(long reviewId, long userId) throws ReviewNotFound, UserNotFound {
        getReviewById(reviewId);
        userService.getUserById(userId);
        String sqlLike = "INSERT INTO REVIEWS_USERS (REVIEW_ID, USER_ID) VALUES (?, ?)";
        String sqlReviewUseful = "UPDATE REVIEWS SET USEFUL = USEFUL + 1 WHERE REVIEW_ID = ?";
        jdbcTemplate.update(sqlLike, reviewId, userId);
        jdbcTemplate.update(sqlReviewUseful, reviewId);
        return getReviewById(reviewId);
    }

    @Override
    public Review addDislikeReview(long reviewId, long userId) throws ReviewNotFound, UserNotFound {
        getReviewById(reviewId);
        userService.getUserById(userId);
        String sqlLike = "INSERT INTO REVIEWS_USERS (REVIEW_ID, USER_ID) VALUES (?, ?)";
        String sqlReviewUseful = "UPDATE REVIEWS SET USEFUL = USEFUL - 1 WHERE REVIEW_ID = ?";
        jdbcTemplate.update(sqlLike, reviewId, userId);
        jdbcTemplate.update(sqlReviewUseful, reviewId);
        return getReviewById(reviewId);
    }

    @Override
    public void deleteLikeReview(long reviewId, long userId) throws ReviewNotFound, UserNotFound {
        getReviewById(reviewId);
        userService.getUserById(userId);
        String sqlLike = "DELETE FROM REVIEWS_USERS WHERE REVIEW_ID = ? AND USER_ID = ?";
        String sqlReviewUseful = "UPDATE REVIEWS SET USEFUL = USEFUL - 1 WHERE REVIEW_ID = ?";
        jdbcTemplate.update(sqlLike, reviewId, userId);
        jdbcTemplate.update(sqlReviewUseful, reviewId);
    }

    @Override
    public void deleteDislikeReview(long reviewId, long userId) throws ReviewNotFound, UserNotFound {
        getReviewById(reviewId);
        userService.getUserById(userId);
        String sqlLike = "DELETE FROM REVIEWS_USERS WHERE REVIEW_ID = ? AND USER_ID = ?";
        String sqlReviewUseful = "UPDATE REVIEWS SET USEFUL = USEFUL + 1 WHERE REVIEW_ID = ?";
        jdbcTemplate.update(sqlLike, reviewId, userId);
        jdbcTemplate.update(sqlReviewUseful, reviewId);
    }

}
