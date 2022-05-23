package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserStorage inMemoryUserStorage;

    @Autowired
    public UserService(UserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User createUser(User user) {
        return inMemoryUserStorage.createUser(user);
    }

    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    public void addFriend(int userId, int friendId) {
        inMemoryUserStorage.findById(userId).friends.add(friendId);
        inMemoryUserStorage.findById(friendId).friends.add(userId);
    }

    public void removeFriend(int userId, int friendId) {
        inMemoryUserStorage.findById(userId).friends.remove(friendId);
    }

    public List<User> allFriends(int userId) {
        List<User> friendsList = new ArrayList<>();
        for (Integer f : inMemoryUserStorage.findById(userId).friends) {
            friendsList.add(inMemoryUserStorage.findById(f));
        }
        return friendsList;
    }

    public  List<User> commonFriends(int userId, int otherId) {
        List<User> commonList = new ArrayList<>();
        for (Integer u : inMemoryUserStorage.findById(userId).friends) {
            for (Integer o : inMemoryUserStorage.findById(otherId).friends) {
                if (u == o) {
                    commonList.add(inMemoryUserStorage.findById(u));
                }
            }
        }
        return commonList;
    }

    public User getUserById(int userId) {
        return inMemoryUserStorage.findById(userId);
    }

    public List<User> getAll() {
        return inMemoryUserStorage.getAllUsers();
    }

}
