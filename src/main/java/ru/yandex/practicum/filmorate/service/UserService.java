package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.userStorage = inMemoryUserStorage;
    }

    public User addUser(User user) {
        userStorage.addUser(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(long userId) throws UserNotFound {
       return userStorage.getUserById(userId);
    }

    public void updateUser(User user) throws UserNotFound {
        userStorage.updateUser(user);
    }

    public void deleteUser(long id) throws UserNotFound {
        userStorage.deleteUser(id);
    }

    public void addFriend(long userId, long friendId) throws UserNotFound {
        if (userStorage.getUserById(friendId) != null) {
            userStorage.getUserById(userId).getFriends().add(friendId);
            userStorage.getUserById(friendId).getFriends().add(userId);
        }
    }

    public void deleteFriend(long userId, long friendId) throws UserNotFound {
        if (userStorage.getUserById(friendId) != null) {
            userStorage.getUserById(userId).getFriends().remove(friendId);
            userStorage.getUserById(friendId).getFriends().remove(userId);
        }
    }

    public List<User> getAllFriends(long userId) throws UserNotFound {
        Set<Long> friends = userStorage.getUserById(userId).getFriends();// {2, 3, 6}.stream.map(2 -> user2)
        return friends.stream().map(userStorage::getUserById).collect(Collectors.toList());
    }

    public List<User> getAllCommonFriends(long userId, long otherUserId) throws UserNotFound {
        List<User> friendsUser = getAllFriends(userId);
        List<User> friendsOtherUser = getAllFriends(otherUserId);
        friendsUser.retainAll(friendsOtherUser);
        return friendsUser;
    }
}
