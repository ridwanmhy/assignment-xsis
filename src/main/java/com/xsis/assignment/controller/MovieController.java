package com.xsis.assignment.controller;

import com.xsis.assignment.domain.Movie;
import com.xsis.assignment.dto.ErrorResponseDTO;
import com.xsis.assignment.dto.RequestMovieDTO;
import com.xsis.assignment.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/Movies")
@Validated
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public Flux<Movie> getAllMovies() {
        return movieService.getAllMovies()
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies found")));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Movie>> getMovieById(@PathVariable Integer id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found")));
    }

    @PostMapping
    public Mono<Movie> createMovie(@Valid @RequestBody Mono<RequestMovieDTO> movieDTO) {
        return movieService.createMovieFromDTO(movieDTO);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Movie>> updateMovie(@PathVariable Integer id, @RequestBody Mono<RequestMovieDTO> movieDTO) {
        return movieService.updateMovieFromDTO(id, movieDTO)
                .map(updatedMovie -> ResponseEntity.ok(updatedMovie))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found")));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteMovie(@PathVariable Integer id) {
        return movieService.deleteMovieConditional(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}

