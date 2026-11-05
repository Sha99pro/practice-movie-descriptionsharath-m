package com.example.demo.controllers;
import java.io.IOException;
import java.util.List;
import com.example.demo.models.Movie;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.services.ReviewService;
import org.apache.http.HttpException;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.GeminiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieRepository movieRepository;
    private final GeminiService geminiService;
    private final ReviewService reviewService;

    public MovieController(MovieRepository movieRepository, GeminiService geminiService, ReviewService reviewService) {
        this.movieRepository = movieRepository;
        this.geminiService = geminiService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) throws IOException, HttpException {
        String description = geminiService.generateDescription(movie.getTitle());
        movie.setDescription(description);
        return movieRepository.save(movie);
    }

    @PostMapping("/{id}/review")
    public Movie generateMovieReview(@PathVariable int id) throws IOException, HttpException {
        Movie movie = movieRepository.findById(id).orElseThrow();
        String review = reviewService.generateReview(movie.getTitle());
        System.out.println("Generated Review: " + review);
        movie.setReview(review);
        return movieRepository.save(movie);
    }

}
