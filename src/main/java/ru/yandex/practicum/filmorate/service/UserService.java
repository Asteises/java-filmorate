package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserDbStorage;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserDbStorage userDbStorage;

    public User addUser(User user) {
        userDbStorage.addUser(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userDbStorage.getAllUsers();
    }

    public User getUserById(long userId) throws UserNotFound {
       return userDbStorage.getUserById(userId);
    }

    public void updateUser(User user) throws UserNotFound {
        userDbStorage.updateUser(user);
    }

    public void deleteUser(long id) throws UserNotFound {
        userDbStorage.deleteUser(id);
    }

}
