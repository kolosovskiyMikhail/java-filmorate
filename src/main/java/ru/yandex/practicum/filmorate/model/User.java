package ru.yandex.practicum.filmorate.model;

import lombok.*;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\S*$")
    private String login;

    private String name;

    private String birthday;

    public User(String email, String login, String name, String birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

}
