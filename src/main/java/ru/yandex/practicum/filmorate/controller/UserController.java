package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Getter
public class UserController extends AbstractController<User>{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Override
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/users")
    @Override
    public User create(@Validated @RequestBody User user) {
        User newUser = userService.createUser(user);
        log.info("Пользователь успешно добавлен");
        return newUser;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        if ((id <= 0) || (friendId <= 0)) {
            throw new RuntimeException("Не такого пользователя");
        }
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId){
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getAllFriends(@PathVariable int id) {
        if (id <= 0) {
            throw new RuntimeException("Не такого пользователя");
        }
        return userService.allFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> commonFriends (@PathVariable int id, @PathVariable int otherId) {
        return userService.commonFriends(id, otherId);
    }

    @PutMapping("/users")
    @Override
    public User update(@Valid @RequestBody User user) {
        if (user.getId() <= 0) {
            throw new RuntimeException("Неправильно введены данные");
        }
        User updateUser = userService.updateUser(user);
        log.info("Данные о пользователе обновлены");
        return updateUser;
    }

    @GetMapping("/users/{id}")
    public User getUserById (@PathVariable int id) {
        if (id <= 0) {
            throw new RuntimeException("Не такого пользователя");
        }
        return userService.getUserById(id);
    }
}
