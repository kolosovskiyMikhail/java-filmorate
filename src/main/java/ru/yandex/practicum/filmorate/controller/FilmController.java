package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class FilmController extends AbstractController<Film> {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    @Override
    public List<Film> getAll() {
        return filmService.getAllFilms();
    }

    @PostMapping("/films")
    @Override
    public Film create(@Valid @RequestBody Film film) {
        log.info("Фильм добавлен");
        return filmService.createFilm(film);
    }

    @PutMapping("/films")
    @Override
    public Film update(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void putLike(@PathVariable int id, @PathVariable int userId) {
        if ((id <= 0) || (userId <= 0)) {
            throw new RuntimeException("Неправильно введены данные");
        }
        filmService.putLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        if ((id <= 0) || (userId <= 0)) {
            throw new RuntimeException("Неправильно введены данные");
        }
        filmService.removeLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> mostPopularFilms(@RequestParam(required = false) String count) {
        List<Film> popularList = new ArrayList<>();
        if (!(count == null)) {
            popularList.addAll(filmService.mostPopularFilms(Integer.parseInt(count)));
        } else popularList.addAll(filmService.mostPopularFilms(0));
        return popularList;
    }

    @GetMapping("/films/{id}")
    public Film getById(@PathVariable int id) {
        if (id <= 0) {
            throw new RuntimeException("Неправильно введены данные");
        }
        return filmService.getById(id);
    }
}
