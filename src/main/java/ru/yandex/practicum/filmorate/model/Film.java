package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Film {
    private int id;

    public Set<Integer> usersWhoLiked = new HashSet<>();

    @NotBlank
    private String name;

    @Length(min =1, max = 200)
    @NotBlank
    private String description;

    private String releaseDate;

    @Min(1)
    private int duration;

    public Film(String name, String description, String releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public int likeCount() {
        return usersWhoLiked.size();
    }
}
