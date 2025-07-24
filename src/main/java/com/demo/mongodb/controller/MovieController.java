package com.demo.mongodb.controller;

import com.demo.mongodb.model.Movie;
import com.demo.mongodb.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // C - Create/Save
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieService.saveMovie(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    // R - Read by ID (MongoDB的_id)
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        return movieService.getMovieById(id)
                .map(movie -> new ResponseEntity<>(movie, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // R - Read by year and title (自定义查询)
    @GetMapping("/year/{year}/{title}")
    public ResponseEntity<Movie> getMovieByYearAndTitle(
            @PathVariable Integer year,
            @PathVariable String title) {
        return movieService.getMovieByYearAndTitle(year, title)
                .map(movie -> new ResponseEntity<>(movie, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // R - Query by year
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Movie>> getMoviesByYear(@PathVariable Integer year) {
        List<Movie> movies = movieService.getMoviesByYear(year);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // R - Query by director
    @GetMapping("/director/{director}")
    public ResponseEntity<List<Movie>> getMoviesByYear(@PathVariable String director) {
        List<Movie> movies = movieService.getMoviesByDirector(director);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // R - Scan all
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // U - Update (覆盖式更新，提供完整对象，如果_id不存在则创建)
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String id, @RequestBody Movie movie) {
        movie.setId(id); // 确保请求体中的ID与路径变量匹配
        Movie updatedMovie = movieService.saveMovie(movie);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }

    // U - 原子递增Likes (PATCH请求)
    @PatchMapping("/{id}/like")
    public ResponseEntity<Movie> likeMovie(@PathVariable String id) {
        return movieService.incrementMovieLikes(id)
                .map(movie -> new ResponseEntity<>(movie, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // D - Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
    }

    // D - Delete all
    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllMovies() {
        movieService.deleteAllMovies();
    }
}