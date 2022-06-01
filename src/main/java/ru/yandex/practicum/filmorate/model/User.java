package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.utils.NoWhiteSpace;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @NoWhiteSpace
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
}
