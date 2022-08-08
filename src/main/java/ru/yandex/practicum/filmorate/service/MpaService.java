package ru.yandex.practicum.filmorate.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.MpaDbStorage;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MpaService {

    private final MpaDbStorage mpaDbStorage;

    public Mpa getMpaById(int mpaId) {
        return mpaDbStorage.getMpaById(mpaId);
    }

    public List<Mpa> getAllMpa() {
        return mpaDbStorage.getAllMpa();
    }

}
