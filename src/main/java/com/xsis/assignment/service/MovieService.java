package com.xsis.assignment.service;
import com.xsis.assignment.domain.Movie;
import com.xsis.assignment.dto.RequestMovieDTO;
import com.xsis.assignment.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Flux<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Mono<Movie> getMovieById(Integer id) {
        return movieRepository.findById(id);
    }

    public Mono<Movie> createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Mono<Movie> createMovieFromDTO(Mono<RequestMovieDTO> movieDTOMono) {
        return movieDTOMono.flatMap(this::DTOTODomain).flatMap(movieRepository::save);
    }

    public Mono<Movie> updateMovie(Integer id, Movie movie) {
        return movieRepository.findById(id)
                .flatMap(existingMovie -> {
                    existingMovie.setTitle(movie.getTitle());
                    existingMovie.setDescription(movie.getDescription());
                    existingMovie.setRating(movie.getRating());
                    existingMovie.setImage(movie.getImage());
                    existingMovie.setUpdatedAt(movie.getUpdatedAt());
                    return movieRepository.save(existingMovie);
                });
    }

    public Mono<Movie> updateMovieFromDTO(Integer id, Mono<RequestMovieDTO> movieDTOMono) {
        return movieDTOMono.flatMap(movieDto -> movieRepository.findById(id)
                .flatMap(existingMovie -> {
                    existingMovie.setTitle(movieDto.getTitle());
                    existingMovie.setDescription(movieDto.getDescription());
                    existingMovie.setRating(movieDto.getRating());
                    existingMovie.setImage(movieDto.getImage());
                    existingMovie.setUpdatedAt(movieDto.getUpdated_at());
                    return movieRepository.save(existingMovie);
                }));
    }

    public Mono<Void> deleteMovie(Integer id) {
        return movieRepository.deleteById(id);
    }

    public Mono<Void> deleteMovieConditional(Integer id) {
        return movieRepository.findById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"))).flatMap(
                existingMovie -> {
                    return movieRepository.deleteById(existingMovie.getId());
                }
        );
    }

    public Mono<Movie> DTOTODomain(RequestMovieDTO movieDto) {
        return Mono.just(new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDescription(),
                movieDto.getRating(),
                movieDto.getImage(),
                movieDto.getCreated_at(),
                movieDto.getUpdated_at()
        ));
    }
}

