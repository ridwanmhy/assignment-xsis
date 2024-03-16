package com.xsis.assignment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;


public class RequestMovieDTO {

    @NotNull(message = "title tidak boleh null")
    @NotBlank(message = "title tidak boleh kosong")
    @Size(max = 255, message = "title max karakter 255")
    private String title;

    @NotNull(message = "description tidak boleh null")
    @NotBlank(message = "description tidak boleh blank")
    private String description;

    @NotNull(message = "rating tidak boleh null")
    @Min(value = 1, message = "rating minimal harus 1")
    @Max(value = 10, message = "rating maximal harus 10")
    private Float rating;

    @NotNull(message = "image tidak boleh kosong")
    private String image;

    @NotNull(message = "created_at tidak boleh kosong")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;

    @NotNull(message = "update_at tidak boleh kosong")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated_at;

    public RequestMovieDTO(){}

    public RequestMovieDTO(String title, String description, Float rating, String image, LocalDateTime created_at, LocalDateTime updated_at) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.image = image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
