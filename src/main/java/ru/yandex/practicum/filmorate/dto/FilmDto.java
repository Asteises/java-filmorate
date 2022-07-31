package ru.yandex.practicum.filmorate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.utils.DateAfter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class FilmDto {

    private long id;
    @NotBlank
    @NotNull
    private String name;
    private long genre;
    private long mpa;
    @Size(max = 200, message = "не может быть больше 200")
    private String description;
    @DateAfter
    private LocalDate releaseDate;
    @Min(1)
    private int duration;
    private Integer likes;

}
