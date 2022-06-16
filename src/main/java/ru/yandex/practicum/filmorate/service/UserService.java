package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {

    private final InMemoryUserStorage inMemoryUserStorage;

    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void addFriend(long userId, long friendId) {
        if (inMemoryUserStorage.getUsers().containsKey(userId) &&
                inMemoryUserStorage.getUsers().containsKey(friendId)) {
            inMemoryUserStorage.getUsers().get(userId).getFriends().add(friendId);
        }
        log.info("User not found: {} or {}", userId, friendId);
        throw new RuntimeException("User не найден");
    }

    public void deleteFriend(long userId, long friendId) {
        if (inMemoryUserStorage.getUsers().containsKey(userId) &&
                inMemoryUserStorage.getUsers().containsKey(friendId)) {
            inMemoryUserStorage.getUsers().get(userId).getFriends().remove(friendId);
        }
        log.info("User not found: {} or {}", userId, friendId);
        throw new RuntimeException("User не найден");
    }

    public List<User> getAllFriends(long userId) {
        List<User> friends = new ArrayList<>();
        if (inMemoryUserStorage.getUsers().containsKey(userId)) {
            for (Long id: inMemoryUserStorage.getUsers().get(userId).getFriends()) {
                if (inMemoryUserStorage.getUsers().containsKey(id)) {
                    friends.add(inMemoryUserStorage.getUsers().get(id));
                }
            }
            return friends;
        }
        log.info("User not found: {}", userId);
        throw new RuntimeException("User не найден");
    }

    public List<User> getAllCommonFriends(long userId, long otherUserId) {
        List<User> commonFriends = new ArrayList<>();
        User user = inMemoryUserStorage.getUsers().get(userId);
        if (inMemoryUserStorage.getUsers().containsKey(userId) && inMemoryUserStorage.getUsers().containsKey(otherUserId)) {
            for (Long id: user.getFriends()) {
                if (inMemoryUserStorage.getUsers().get(otherUserId).getFriends().contains(id)) {
                    commonFriends.add(user);
                }
            }
            return commonFriends;
        }
        log.info("User not found: {} or {}", userId, otherUserId);
        throw new RuntimeException("User не найден");
    }
}
