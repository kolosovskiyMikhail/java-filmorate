package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.expection.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController extends AbstractController<Film> {
    private final HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping("/films")
    @Override
    public Film create(@Valid @RequestBody Film film) {
        filmValidate(film);
        film.setId(id++);
        films.put(film.getId(), film);
        log.info("Фильм добавлен");
        return film;
    }

    @PutMapping("/films")
    @Override
    public void update(@Valid @RequestBody Film film) {
        filmValidate(film);
        films.put(film.getId(), film);
        log.info("Данные о фильме обновлены");
    }

    public LocalDate dateFormatter (String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(str, formatter);
    }

    public void filmValidate(Film film) {
        if (dateFormatter(film.getReleaseDate()).isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Слишком старый фильм");
        }
    }
}
