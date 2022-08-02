package ru.yandex.practicum.filmorate.repository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Data
@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO USERS (email, login, name, birthday) VALUES (?, ?, ?, ?)";
        String sqlId = "SELECT MAX(ID) FROM USERS";
        if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        user.setId(jdbcTemplate.queryForObject(sqlId, Long.class));
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User getUserById(long id) throws UserNotFound {
        try {
            String sql = "SELECT * FROM USERS WHERE ID = ?";
            String sqlFriends = "SELECT * FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ? AND STATUS IS TRUE)";
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
            List<User> userFriends = jdbcTemplate.query(sqlFriends, new UserRowMapper(), id);
            user.setFriends(userFriends);
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFound("");
        }
    }

    @Override
    public User updateUser(User user) throws UserNotFound {
            getUserById(user.getId());
            String sql = "UPDATE USERS SET " +
                    "EMAIL = ?, " +
                    "LOGIN = ?, " +
                    "NAME = ?, " +
                    "BIRTHDAY = ? " +
                    "WHERE ID = ?";
            jdbcTemplate.update(sql,
                    user.getEmail(),
                    user.getLogin(),
                    user.getName(),
                    user.getBirthday(),
                    user.getId()
            );
            return user;
    }

    @Override
    public void deleteUser(long id) throws UserNotFound {
        String sql = "DELETE FROM USERS WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }

    public void addFriend(long userId, long friendId) throws UserNotFound {
        getUserById(userId);
        getUserById(friendId);
        String sql = "INSERT INTO FRIENDS (USER_ID, FRIEND_ID, STATUS) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, friendId, Boolean.TRUE);
    }

    public void deleteFriend(long userId, long friendId) throws UserNotFound {
        String sql = "UPDATE FRIENDS SET STATUS = 0 WHERE FRIEND_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    public List<User> getAllFriends(long id) throws UserNotFound {
        String sqlFriend = "SELECT * FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ? AND STATUS IS TRUE)";
        return jdbcTemplate.query(sqlFriend, new UserRowMapper(), id);
    }

    public List<User> getAllCommonFriends(long userId, long otherUserId) throws UserNotFound {
        String sqlFriend = "SELECT * FROM (SELECT * FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ? AND STATUS IS TRUE)) t1 " +
                "JOIN (SELECT * FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ? AND STATUS IS TRUE)) t2 ON t2.ID=t1.ID";
        return jdbcTemplate.query(sqlFriend, new UserRowMapper(), userId, otherUserId);
    }

    public void addLike(long userId, long filmId) throws UserNotFound, FilmNotFound {
        String sql = "MERGE INTO LIKES (USER_ID, FILM_ID) KEY (USER_ID) VALUES ( ?, ? )";
        jdbcTemplate.update(sql, userId, filmId);
    }

    public void deleteLikeFromFilm(long filmId, long userId) throws FilmNotFound, UserNotFound {
        String sql = "DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }
}
