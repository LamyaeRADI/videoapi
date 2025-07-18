package com.example.videoapi.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("movie")
@Entity
@Getter
@Setter
public class Movie extends Video {
    @NotBlank
    private String director;

    @NotNull
    private Instant releaseDate;

    public Movie(String id, String title, List<String> labels, String director, Instant releaseDate) {
        super(id, title, labels);
        this.director = director;
        this.releaseDate = releaseDate;
    }

}
