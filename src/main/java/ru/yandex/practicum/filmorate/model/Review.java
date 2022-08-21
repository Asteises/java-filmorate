package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "reviewId") // Объект определяется только по полю id
@ToString
public class Review {

    private long reviewId;
    private int useful;
    private boolean isPositive;
    private String content;
    private long userId;
    private long filmId;

}
