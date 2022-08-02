package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.FilmNotFound;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
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

    /**
     * Добавляем нового User
     */
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    /**
     * Получаем всех User
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * Получаем User по id
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) throws UserNotFound {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    /**
     * Изменяем существующего User
     */
    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws UserNotFound {
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    /**
     * Удаляем User по id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws UserNotFound {
        userService.deleteUser(id);
        return ResponseEntity.ok("");
    }

    /**
     * Добавляем User в друзья к другому User
     */
    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> addFriend(@PathVariable long id, @PathVariable long friendId) throws UserNotFound {
        userService.addFriend(id, friendId);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Удаляем User из друзей другого User
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable long id, @PathVariable long friendId) throws UserNotFound {
        userService.deleteFriend(friendId, id);
        return ResponseEntity.ok("Friend has been deleted");
    }

    /**
     * Получаем всех друзей User по id
     */
    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getAllFriends(@PathVariable long id) throws UserNotFound {
        return new ResponseEntity<>(userService.getAllFriends(id), HttpStatus.OK);
    }

    /**
     * Получаем всех общих друзей двух User
     */
    @GetMapping("/{userId}/friends/common/{friendId}")
    public ResponseEntity<List<User>> getAllCommonFriends(@PathVariable long userId, @PathVariable long friendId) throws UserNotFound {
        return new ResponseEntity<>(userService.getAllCommonFriends(userId, friendId), HttpStatus.OK);
    }

    /**
     * Добавляем like к Film
     */
    @PostMapping("/{userId}/like/{filmId}")
    public ResponseEntity<String> addLike(@PathVariable long userId, @PathVariable long filmId) throws UserNotFound, FilmNotFound {
        userService.addLikeToFilm(userId, filmId);
        return ResponseEntity.ok("Лайк добавлен");
    }

    /**
     * Удаляем like у Film
     */
    @DeleteMapping("/{userId}/like/delete/{filmId}")
    public ResponseEntity<String> deleteLikeFromFilm(@PathVariable long userId, @PathVariable long filmId) throws UserNotFound, FilmNotFound {
        userService.deleteLikeFromFilm(userId, filmId);
        return ResponseEntity.ok("Like delete");
    }
}
