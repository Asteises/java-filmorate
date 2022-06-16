package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Service
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users;

    public InMemoryUserStorage(Map<Long, User> users) {
        this.users = users;
    }

    @Override
    public User addUser(User user) {
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

    public User getUserById(long userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        }
        log.info("User not found: {}", userId);
        throw new RuntimeException("User не найден");
    }

    @Override
    public User updateUser(User user) {
        if (users.containsValue(user)) {
            users.put(user.getId(), user);
            log.info("User has been updated: {}", user);
            return user;
        }
        log.info("User not found: {}", user.getId());
        throw new RuntimeException("User не найден");
    }

    @Override
    public void deleteUser(long userId) {
        if (users.containsKey(userId)) {
            users.remove(userId);
            log.info("User delete");
        }
        log.info("User not found: {}", userId);
        throw new RuntimeException("User не найден");
    }
}
