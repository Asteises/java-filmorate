package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users;

    private long id = 1;

    public InMemoryUserStorage() {
        users = new HashMap<>();
    }

    @Override
    public User addUser(User user) {
        user.setId(id++);
        if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("User has been created: {}", user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(long userId) throws UserNotFound {
        if (users.containsKey(userId)) {
            return users.get(userId);
        }
        log.info("User not found: {}", userId);
        throw new UserNotFound("User не найден");
    }

    @Override
    public User updateUser(User user) throws UserNotFound {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("User has been updated: {}", user);
            return user;
        }
        log.info("User not found: {}", user.getId());
        throw new UserNotFound("User не найден");
    }

    @Override
    public void deleteUser(long userId) throws UserNotFound {
        if (users.containsKey(userId)) {
            users.remove(userId);
            log.info("User delete");
        }
        log.info("User not found: {}", userId);
        throw new UserNotFound("User не найден");
    }
}
