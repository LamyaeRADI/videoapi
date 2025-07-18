package com.example.videoapi.controller;

import com.example.videoapi.model.*;
import com.example.videoapi.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService service;

    public VideoController(VideoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> addVideo(@RequestBody @Valid Video video) {
        service.addVideo(video);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getById(@PathVariable String id) {
        return service.getVideoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Video> searchByTitle(@RequestParam String keyword) {
        return service.searchByTitle(keyword);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deleted")
    public List<String> getDeleted() {
        return service.getDeletedIds();
    }

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return service.getMovies();
    }

    @GetMapping("/series")
    public List<Series> getSeries() {
        return service.getSeries();
    }

    @GetMapping("/{id}/recommendations")
    public List<Video> recommend(@PathVariable String id,
                                 @RequestParam(defaultValue = "1") int minCommonLabels) {
        return service.recommendSimilarVideos(id, minCommonLabels);
    }
}
