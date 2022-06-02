package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
public class FilmTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void addFilmTest() {
        Film film = new Film(1, "", "12312", LocalDate.of(1900,10,10), 100);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("должно быть не меньше 1")));
    }
}
