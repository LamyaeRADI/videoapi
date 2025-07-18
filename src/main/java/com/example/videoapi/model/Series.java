package com.example.videoapi.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Entity
@DiscriminatorValue("series")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Series extends Video {
    @Min(1)
    private int numberOfEpisodes;

    public Series(String id, String title, List<String> labels, int numberOfEpisodes) {
        super(id, title, labels);
        this.numberOfEpisodes = numberOfEpisodes;
    }
}
