package com.example.demo.controllers;
import java.io.IOException;
import java.util.List;
import com.example.demo.models.Movie;
import com.example.demo.repositories.MovieRepository;
import org.apache.http.HttpException;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.GeminiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieRepository movieRepository;
    private final GeminiService geminiService;

    public MovieController(MovieRepository movieRepository, GeminiService geminiService) {
        this.movieRepository = movieRepository;
        this.geminiService = geminiService;
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
}
