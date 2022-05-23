package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.expection.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage{
    private final HashMap<Integer, User> users = new HashMap<>();
    int id = 1;


    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) {
        userValidate(user);
        user.setId(id++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        userValidate(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User findById(int id) {
        return users.get(id);
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
