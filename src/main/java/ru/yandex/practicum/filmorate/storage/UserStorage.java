package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User addUser(User user);

    List<User> getAllUsers();

    User getUserById(long id);

    User updateUser(User user);

    void deleteUser(long userId);
}
