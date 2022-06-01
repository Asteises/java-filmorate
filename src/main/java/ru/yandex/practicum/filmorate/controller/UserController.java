package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private List<User> users = new ArrayList<>();

    @PostMapping
    public User add(@Valid @RequestBody User user) {
        users.add(user);
        return user;
    }

    @GetMapping
    public List<User> getAll() {
        return users;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        for (User u: users) {
            if (u.getId() == user.getId()) {
                u.setBirthday(user.getBirthday());
                u.setEmail(user.getEmail());
                u.setLogin(user.getLogin());
                u.setName(user.getName());
                return u;
            }
        }
        log.info("User not found: {}", user.getId());
        return null;
    }
}
