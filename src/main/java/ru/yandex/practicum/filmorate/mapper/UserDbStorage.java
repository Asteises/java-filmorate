package ru.yandex.practicum.filmorate.mapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
                );
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User getUserById(long id) throws UserNotFound {
        String sql = "SELECT * FROM USERS WHERE ID = ?";
        String sqlJoin = "SELECT * FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?)";
        User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        List<User> userFriends = jdbcTemplate.query(sqlJoin, new UserRowMapper(), id);
        user.setFriends(userFriends);
        return user;
    }

    @Override
    public User updateUser(User user) throws UserNotFound {
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
        String sql = "INSERT INTO FRIENDS VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, friendId, Boolean.TRUE);
        jdbcTemplate.update(sql, friendId, userId, Boolean.FALSE);
    }

    public void deleteFriend(long friendId, long userId) throws UserNotFound {

        String sql = "DELETE FROM FRIENDS WHERE FRIEND_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql, friendId, userId);
    }

    public List<User> getAllFriends(long id) throws UserNotFound {
        String sqlJoin = "SELECT * FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?)";
        List<User> userFriends = jdbcTemplate.query(sqlJoin, new UserRowMapper(), id);
        return userFriends;
    }

    public List<User> getAllCommonFriends(long friendId, long userId) throws UserNotFound {
        String sqlJoin = "SELECT * FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?)";
        List<User> commonFriends = jdbcTemplate.query(sqlJoin, new UserRowMapper(), userId, friendId);
        return commonFriends;
    }
}
