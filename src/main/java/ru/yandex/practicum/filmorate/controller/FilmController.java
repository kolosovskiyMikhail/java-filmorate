package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.expection.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    int id = 1;

    @GetMapping("/films")
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping("/films")
    public void createFilm(@RequestBody Film film) {
        filmValidate(film);
        film.setId(id++);
        films.put(film.getId(), film);
        log.info("Фильм добавлен");
    }

    @PutMapping("/films")
    public void updateFilm(@RequestBody Film film) {
        filmValidate(film);
        films.put(film.getId(), film);
        log.info("Данные о фильме обновлены");
    }

    public LocalDate dateFormatter (String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(str, formatter);
    }

    public void filmValidate(Film film) {
        try {
            if (film.getName().isBlank()) {
                throw new ValidationException("Название фильма не может быть пустым");
            }
            if (film.getDescription().length() >= 200) {
                throw new ValidationException("Слишком длинное описание");
            }
            if (dateFormatter(film.getReleaseDate()).isBefore(LocalDate.of(1895, 12, 28))) {
                throw new ValidationException("Слишком старый фильм");
            }
            if (film.getDuration() <= 0) {
                throw new ValidationException("Неверная продолжительность фильма");
            }
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
