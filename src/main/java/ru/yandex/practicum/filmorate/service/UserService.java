package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.userStorage = inMemoryUserStorage;
    }

    public void addUser(User user) {
        userStorage.addUser(user);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(long userId) {
       return userStorage.getUserById(userId);
    }

    public void updateUser(User user) {
        userStorage.updateUser(user);
    }

    public void deleteUser(long id) {
        userStorage.deleteUser(id);
    }

    public void addFriend(long userId, long friendId) throws UserNotFound {
        userStorage.getUserById(userId).getFriends().add(friendId);
        userStorage.getUserById(friendId).getFriends().add(userId);

        log.info("User not found: {} or {}", userId, friendId);
        throw new UserNotFound("User не найден");
    }

    public void deleteFriend(long userId, long friendId) {
        userStorage.getUserById(userId).getFriends().remove(friendId);
        userStorage.getUserById(friendId).getFriends().remove(userId);

        log.info("User not found: {} or {}", userId, friendId);
        throw new RuntimeException("User не найден");
    }

    public List<User> getAllFriends(long userId) {
        List<User> friends = new ArrayList<>();
        if (userStorage.getAllUsers().contains(userStorage.getUserById(userId))) {
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
