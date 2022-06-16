package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exeption.UserNotFound;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final InMemoryUserStorage inMemoryUserStorage;

    private final UserService userService;

    public UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
        inMemoryUserStorage.addUser(user);
        return ResponseEntity.ok(user.getId() + " User has been created");
    }

    @GetMapping
    public ResponseEntity<String> getAllUsers() {
        inMemoryUserStorage.getAllUsers();
        return ResponseEntity.ok("All users found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable long id) {
        inMemoryUserStorage.getUserById(id);
        return ResponseEntity.ok("User found");
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user) {
        inMemoryUserStorage.updateUser(user);
        return ResponseEntity.ok(user.getId() + " User has been updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        inMemoryUserStorage.deleteUser(id);
        return ResponseEntity.ok("User delete");
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable long id, @PathVariable long friendId) throws UserNotFound {
        userService.addFriend(id, friendId);
        return ResponseEntity.ok("Friend has been added");
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFriend(id, friendId);
        return ResponseEntity.ok("Friend has been deleted");
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<String> getAllFriends(@PathVariable long id) {
        userService.getAllFriends(id);
        return ResponseEntity.ok("All friends found");
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<String> getAllCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        userService.getAllCommonFriends(id, otherId);
        return ResponseEntity.ok("All common friends found");
    }
}
