package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;

@Service
public class FilmService {
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final int COUNT_WHEN_ZERO = 10;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public Film createFilm(Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }

    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film getById(int id) {
        return inMemoryFilmStorage.findById(id);
    }

    public void putLike(int filmId, int userId) {
        inMemoryFilmStorage.findById(filmId).usersWhoLiked.add(userId);
    }

    public void removeLike(int filmId, int userId) {
        inMemoryFilmStorage.findById(filmId).usersWhoLiked.remove(userId);
    }

    public List<Film> mostPopularFilms(int count) {
        Comparator<Film> comparator = Comparator.comparing(Film::likeCount).reversed();
        List<Film> popularFilms = new ArrayList<>(inMemoryFilmStorage.getAllFilms());
        popularFilms.sort(comparator);
        if (count > 0) {
            while (popularFilms.size() > count) {
                popularFilms.remove(popularFilms.size() - 1);
            }
        } else if (count == 0) {
            while (popularFilms.size() >=COUNT_WHEN_ZERO) {
                popularFilms.remove(popularFilms.size() - 1);
            }
        }
        return popularFilms;
    }
}
