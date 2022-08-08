package ru.yandex.practicum.filmorate.dbStorageTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final UserDbStorage userStorage;

    @Test
    public void testAddUser() {
        User user = getTestUser();
        User actualUser = userStorage.addUser(user);
        Assertions.assertEquals(user, actualUser);
    }

    @Test
    public void testGetUserById() {
        User user = getTestUser();
        userStorage.addUser(user);
        User actualUser = userStorage.getUserById(1);
        Assertions.assertEquals(user, actualUser);
    }

    @Test
    public void testGetAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        User user = getTestUser();
        user.setId(1L);
        expectedUsers.add(user);
        user.setId(2L);
        expectedUsers.add(user);
        user.setId(3L);
        expectedUsers.add(user);

        userStorage.addUser(user);
        userStorage.addUser(user);
        userStorage.addUser(user);

        List<User> actualUsers = userStorage.getAllUsers();
        Assertions.assertEquals(3, actualUsers.size());
        Assertions.assertTrue(actualUsers.containsAll(expectedUsers));
    }

    private User getTestUser() {
        User user = new User();
        user.setName("TestName");
        user.setLogin("TestLogin");
        user.setEmail("test@test.ru");
        user.setBirthday(LocalDate.now());
        return user;
    }
}