package com.example.videoapi.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Movie.class, name = "movie"),
        @JsonSubTypes.Type(value = Series.class, name = "series")
})
public abstract class Video {
    @NotNull
    @Id
    private String id;

    @NotBlank
    private String title;

    @NotNull
    private List<String> labels;
}
