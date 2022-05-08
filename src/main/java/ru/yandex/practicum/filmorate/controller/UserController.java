package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.expection.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@Getter
public class UserController extends AbstractController<User>{
    private final HashMap<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping("/users")
    @Override
    public User create(@Validated @RequestBody User user) {
        userValidate(user);
        user.setId(id++);
        users.put(user.getId(), user);
        log.info("Пользователь успешно добавлен");
        return user;
    }

    @PutMapping("/users")
    @Override
    public void update(@Valid @RequestBody User user) {
        userValidate(user);
        users.put(user.getId(), user);
        log.info("Данные о пользователе обновлены");
    }

   public LocalDate dateFormatter (String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(str, formatter);
    }


    public void userValidate(User user) {
        if (dateFormatter(user.getBirthday()).isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения из будущего");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

}
