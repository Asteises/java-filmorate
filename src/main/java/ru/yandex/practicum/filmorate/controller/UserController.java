package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.mapper.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserDbStorage userDbStorage;

    /**
     * Добавляем нового User
     */
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        userDbStorage.addUser(user);
        return user;
    }

    /**
     * Получаем всех User
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userDbStorage.getAllUsers(), HttpStatus.OK);
    }

    /**
     * Получаем User по id
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) throws UserNotFound {
        return new ResponseEntity<>(userDbStorage.getUserById(Long.parseLong(id)), HttpStatus.OK);
    }

    /**
     * Изменяем существующего User
     */
    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws UserNotFound {
        userDbStorage.updateUser(user);
        return ResponseEntity.ok(user);
    }

    /**
     * Удаляем User по id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws UserNotFound {
        userDbStorage.deleteUser(id);
        return ResponseEntity.ok("");
    }

    /**
     * Добавляем User в друзья к другому User
     */
    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> addFriend(@PathVariable long id, @PathVariable long friendId) throws UserNotFound {
        userDbStorage.addFriend(id, friendId);
        return ResponseEntity.ok(userDbStorage.getUserById(id));
    }

    /**
     * Удаляем User из друзей другого User
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable long id, @PathVariable long friendId) throws UserNotFound {
        userDbStorage.deleteFriend(id, friendId);
        return ResponseEntity.ok("Friend has been deleted");
    }

    /**
     * Получаем всех друзей User по id
     */
    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getAllFriends(@PathVariable long id) throws UserNotFound {
        return new ResponseEntity<>(userDbStorage.getAllFriends(id), HttpStatus.OK);
    }

    /**
     * Получаем всех общих друзей двух User
     */
    @GetMapping("/{id}/friends/common/{friendId}")
    public ResponseEntity<List<User>> getAllCommonFriends(@PathVariable long id, @PathVariable long friendId) throws UserNotFound {
        return new ResponseEntity<>(userDbStorage.getAllCommonFriends(id, friendId), HttpStatus.OK);
    }
}
