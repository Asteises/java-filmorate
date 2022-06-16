package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    public User addUser(User user);

    public List<User> getAllUsers();

    public User updateUser(User user);

    public void deleteUser(long userId);

}
