package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
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

    public void addFriend(long userId, long friendId) throws UserNotFound {
        if (inMemoryUserStorage.getUsers().containsKey(userId) &&
                inMemoryUserStorage.getUsers().containsKey(friendId)) {
            inMemoryUserStorage.getUsers().get(userId).getFriends().add(friendId);
            inMemoryUserStorage.getUsers().get(friendId).getFriends().add(userId);
        }
        log.info("User not found: {} or {}", userId, friendId);
        throw new UserNotFound("User не найден");
    }

    public void deleteFriend(long userId, long friendId) {
        if (inMemoryUserStorage.getUsers().containsKey(userId) &&
                inMemoryUserStorage.getUsers().containsKey(friendId)) {
            inMemoryUserStorage.getUsers().get(userId).getFriends().remove(friendId);
            inMemoryUserStorage.getUsers().get(friendId).getFriends().remove(userId);
        }
        log.info("User not found: {} or {}", userId, friendId);
        throw new RuntimeException("User не найден");
    }

    public List<User> getAllFriends(long userId) {
        List<User> friends = new ArrayList<>();
        if (inMemoryUserStorage.getUsers().containsKey(userId)) {
            for (Long id: inMemoryUserStorage.getUsers().get(userId).getFriends()) {
                friends.add(inMemoryUserStorage.getUsers().get(id));
            }
            return friends;
        }
        log.info("User not found: {}", userId);
        throw new RuntimeException("User не найден");
    }

    public List<User> getAllCommonFriends(long userId, long otherUserId) {
        List<User> friendsUser = getAllFriends(userId);
        List<User> friendsOtherUser = getAllFriends(otherUserId);
        friendsUser.retainAll(friendsOtherUser);
        return friendsUser;
    }
}
