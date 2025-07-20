package com.example.videoapi.service;

import com.example.videoapi.model.*;
import com.example.videoapi.data.VideoDataAccess;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoService {

    private final VideoDataAccess videoDataAccess;

    public VideoService(VideoDataAccess videoDataAccess) {
        this.videoDataAccess = videoDataAccess;
    }

    public void addVideo(Video video) {
        // Validate that movie and series are exclusive
        if (!(video instanceof Movie) && !(video instanceof Series)) {
            throw new IllegalArgumentException("Invalid video type");
        }
        videoDataAccess.save(video);
    }

    public Optional<Video> getVideoById(String id) {
        return videoDataAccess.findById(id);
    }

    public List<Video> searchByTitle(String keyword) {
        if (keyword.length() < 3) return List.of();
        return videoDataAccess.findAll().stream()
                .filter(v -> v.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void deleteVideo(String id) {
        videoDataAccess.delete(id);
    }

    public List<String> getDeletedIds() {
        return new ArrayList<>(videoDataAccess.getDeletedIds());
    }

    public List<Movie> getMovies() {
        return videoDataAccess.findAll().stream()
                .filter(v -> v instanceof Movie)
                .map(v -> (Movie) v)
                .collect(Collectors.toList());
    }

    public List<Series> getSeries() {
        return videoDataAccess.findAll().stream()
                .filter(v -> v instanceof Series)
                .map(v -> (Series) v)
                .collect(Collectors.toList());
    }

    public List<Video> recommendSimilarVideos(String id, int minCommonLabels) {
        Optional<Video> baseVideoOpt = videoDataAccess.findById(id);
        if (baseVideoOpt.isEmpty()) return List.of();

        Video baseVideo = baseVideoOpt.get();
        Set<String> baseLabels = new HashSet<>(baseVideo.getLabels());

        return videoDataAccess.findAll().stream()
                .filter(v -> !v.getId().equals(id))
                .filter(v -> {
                    Set<String> common = new HashSet<>(v.getLabels());
                    common.retainAll(baseLabels);
                    return common.size() >= minCommonLabels;
                })
                .collect(Collectors.toList());
    }
}