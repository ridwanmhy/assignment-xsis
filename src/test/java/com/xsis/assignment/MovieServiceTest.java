package com.xsis.assignment;

import com.xsis.assignment.domain.Movie;
import com.xsis.assignment.repository.MovieRepository;
import com.xsis.assignment.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    public void testGetAllMovies() {
        // Mock repository behavior
        when(movieRepository.findAll()).thenReturn(Flux.just(new Movie(), new Movie()));

        // Test service method
        Flux<Movie> result = movieService.getAllMovies();

        // Assert result
        assertEquals(2, result.collectList().block().size());
    }

    @Test
    public void testGetMovieById() {
        // Mock repository behavior
        int movieId = 1;
        Movie movie = new Movie(movieId, "Test Movie", "Description", 8.0f, "image.jpg", LocalDateTime.now(), LocalDateTime.now());
        when(movieRepository.findById(movieId)).thenReturn(Mono.just(movie));

        // Test service method
        Mono<Movie> result = movieService.getMovieById(movieId);

        // Assert result
        assertEquals(movie, result.block());
    }

    @Test
    public void testCreateMovie() {
        // Mock repository behavior
        Movie newMovie = new Movie(null, "New Movie", "Description", 7.5f, "new_image.jpg", LocalDateTime.now(), LocalDateTime.now());
        when(movieRepository.save(newMovie)).thenReturn(Mono.just(newMovie));

        // Test service method
        Mono<Movie> result = movieService.createMovie(newMovie);

        // Assert result
        assertEquals(newMovie, result.block());
    }

    @Test
    public void testUpdateMovie() {
        // Mock repository behavior
        int movieId = 1;
        Movie existingMovie = new Movie(movieId, "Existing Movie", "Description", 8.0f, "existing_image.jpg", LocalDateTime.now(), LocalDateTime.now());
        Movie updatedMovie = new Movie(movieId, "Updated Movie", "Updated Description", 8.5f, "updated_image.jpg", LocalDateTime.now(), LocalDateTime.now());
        when(movieRepository.findById(movieId)).thenReturn(Mono.just(existingMovie));
        when(movieRepository.save(existingMovie)).thenReturn(Mono.just(updatedMovie));

        // Test service method
        Mono<Movie> result = movieService.updateMovie(movieId, updatedMovie);

        // Assert result
        assertEquals(updatedMovie, result.block());
    }

    @Test
    public void testDeleteMovie() {
        // Mock repository behavior
        int movieId = 1;
        Movie movieToDelete = new Movie(movieId, "Movie to Delete", "Description", 7.5f, "image.jpg", LocalDateTime.now(), LocalDateTime.now());
        when(movieRepository.deleteById(movieId)).thenReturn(Mono.empty());

        // Test service method
        Mono<Void> result = movieService.deleteMovie(movieId);

        // Assert result
        verify(movieRepository, times(1)).deleteById(movieId);
    }
}

