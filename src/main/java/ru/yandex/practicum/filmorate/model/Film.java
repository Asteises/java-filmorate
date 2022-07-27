package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.utils.DateAfter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    private long id;
    @NotBlank
    @NotNull
    private String name;
    private String genre;
    private String MPA;
    @Size(max = 200, message = "не может быть больше 200")
    private String description;
    @DateAfter
    private LocalDate releaseDate;
    @Min(1)
    private int duration;
    private Set<Long> likes = new HashSet<>();
}
