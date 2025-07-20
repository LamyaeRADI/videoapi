package com.example.videoapi.service;

import com.example.videoapi.data.VideoDataAccess;
import com.example.videoapi.model.Movie;
import com.example.videoapi.model.Series;
import com.example.videoapi.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VideoServiceTest {

    @Mock
    private VideoDataAccess videoDataAccess;

    @InjectMocks
    private VideoService videoService;

    private Movie movie;
    private Series series;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        movie = new Movie("movie-1", "Inception", List.of("thriller", "dream"),
                "Christopher Nolan", Instant.parse("2010-07-16T00:00:00Z"));

        series = new Series("series-1", "Breaking Bad", List.of("crime", "drug", "desert"), 62);
    }

    @Test
    void testAddVideo_savesMovie() {
        videoService.addVideo(movie);
        verify(videoDataAccess).save(movie);
    }

    @Test
    void testGetVideoById_returnsVideo() {
        when(videoDataAccess.findById("movie-1")).thenReturn(Optional.of(movie));
        Optional<Video> result = videoService.getVideoById("movie-1");
        assertTrue(result.isPresent());
        assertEquals("Inception", result.get().getTitle());
    }

    @Test
    void testSearchByTitle_withMatchingKeyword() {
        when(videoDataAccess.findAll()).thenReturn(List.of(movie, series));
        List<Video> result = videoService.searchByTitle("break");
        assertEquals(1, result.size());
        assertEquals("Breaking Bad", result.get(0).getTitle());
    }

    @Test
    void testSearchByTitle_tooShort() {
        List<Video> result = videoService.searchByTitle("a");
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteVideo_callsDelete() {
        videoService.deleteVideo("movie-1");
        verify(videoDataAccess).delete("movie-1");
    }

    @Test
    void testGetDeletedIds() {
        when(videoDataAccess.getDeletedIds()).thenReturn(Set.of("movie-1", "series-1"));
        List<String> result = videoService.getDeletedIds();
        assertTrue(result.contains("movie-1"));
        assertEquals(2, result.size());
    }

    @Test
    void testGetMovies_returnsOnlyMovies() {
        when(videoDataAccess.findAll()).thenReturn(List.of(movie, series));
        List<Movie> result = videoService.getMovies();
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
    }

    @Test
    void testGetSeries_returnsOnlySeries() {
        when(videoDataAccess.findAll()).thenReturn(List.of(movie, series));
        List<Series> result = videoService.getSeries();
        assertEquals(1, result.size());
        assertEquals("Breaking Bad", result.get(0).getTitle());
    }

    @Test
    void testRecommendSimilarVideos_returnsExpected() {
        Movie another = new Movie("movie-2", "Dreamscape", List.of("dream", "sci-fi"), "Smith", Instant.parse("2010-07-16T00:00:00Z"));
        when(videoDataAccess.findById("movie-1")).thenReturn(Optional.of(movie));
        when(videoDataAccess.findAll()).thenReturn(List.of(movie, another));

        List<Video> result = videoService.recommendSimilarVideos("movie-1", 1);
        assertEquals(1, result.size());
        assertEquals("Dreamscape", result.get(0).getTitle());
    }

    @Test
    void testRecommendSimilarVideos_notFound() {
        when(videoDataAccess.findById("unknown")).thenReturn(Optional.empty());
        List<Video> result = videoService.recommendSimilarVideos("unknown", 1);
        assertTrue(result.isEmpty());
    }

    @Test
    void testAddVideo_withInvalidType_shouldThrowException() {
        // An Invalid Video implementation that is not Movie or Series
        class InvalidVideo extends Video {
            public InvalidVideo(String id, String title, List<String> labels) {
                super(id, title, labels);
            }
        }

        Video invalidVideo = new InvalidVideo("bad-id", "Invalid", List.of("fake"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            videoService.addVideo(invalidVideo);
        });

        assertEquals("Invalid video type", exception.getMessage());
    }

}
