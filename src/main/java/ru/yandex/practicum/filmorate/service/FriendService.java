package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FriendDbStorage;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FriendService {

    private final FriendDbStorage friendDbStorage;

    public void addFriend(long userId, long friendId) throws UserNotFound {
        friendDbStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(long userId, long friendId) throws UserNotFound {
        friendDbStorage.deleteFriend(friendId, userId);
    }

    public List<User> getAllFriends(long userId) throws UserNotFound {
        return friendDbStorage.getAllFriends(userId);
    }

    public List<User> getAllCommonFriends(long userId, long otherUserId) throws UserNotFound {
        return friendDbStorage.getAllCommonFriends(userId, otherUserId);
    }

}
