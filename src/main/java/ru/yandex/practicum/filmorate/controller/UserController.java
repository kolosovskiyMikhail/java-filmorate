package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.expection.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private final List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users;
    }

    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
        userValidate(user);
        users.add(user);
        log.info("Пользователь добавлен");
    }

    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
        userValidate(user);
        for (User u : users) {
            if (user.getId() == u.getId()) {
                users.remove(u);
                users.add(user);
            }
        }
        log.info("Данные о пользователе обновлены");
    }

    public LocalDate dateFormatter (String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(str, formatter);
    }

    public void userValidate(User user) {
        try {
            if (user.getEmail().isBlank()) {
                throw new ValidationException("Электронная почта не может быть пустой");
            }
            if (!user.getEmail().contains("@")) {
                throw new ValidationException("Неверно введена электронная почта");
            }
            if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
                throw new ValidationException("Неверно введен логин");
            }
            if (dateFormatter(user.getBirthday()).isAfter(LocalDate.now())) {
                throw new ValidationException("Дата рождения из будущего");
            }
            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
        } catch (ValidationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
