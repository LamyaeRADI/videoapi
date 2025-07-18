package com.example.videoapi.data;
import com.example.videoapi.model.Video;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VideoDataAccess {
    private final Map<String, Video> videoMap = new ConcurrentHashMap<>();
    private final Set<String> deletedIds = Collections.synchronizedSet(new HashSet<>());

    public void save(Video video) {
        videoMap.put(video.getId(), video);
    }

    public Optional<Video> findById(String id) {
        return Optional.ofNullable(videoMap.get(id));
    }

    public List<Video> findAll() {
        return new ArrayList<>(videoMap.values());
    }

    public void delete(String id) {
        videoMap.remove(id);
        deletedIds.add(id);
    }

    public Set<String> getDeletedIds() {
        return deletedIds;
    }
}
