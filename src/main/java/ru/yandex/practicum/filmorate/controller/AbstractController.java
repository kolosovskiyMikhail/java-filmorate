package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public abstract class AbstractController<T> {
    int id = 1;

    @GetMapping
    public abstract List<T> getAll();

    @PostMapping
    public abstract T create (T obj);

    @PutMapping
    public abstract T update(T obj);


}
