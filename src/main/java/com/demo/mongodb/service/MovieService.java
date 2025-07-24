package com.demo.mongodb.service;

import com.demo.mongodb.model.Movie;
import com.demo.mongodb.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 为MovieRepository生成构造函数，方便依赖注入
public class MovieService {

    private final MovieRepository movieRepository;

    // C - Create/Save
    public Movie saveMovie(Movie movie) {
        // 对于MongoDB，如果_id不存在，save()会创建；如果存在，会更新。
        // 所以可以同时用于创建和部分更新。
        return movieRepository.save(movie);
    }

    // R - Read by ID
    public Optional<Movie> getMovieById(String id) {
        return movieRepository.findById(id);
    }

    // R - Read by year and title
    public Optional<Movie> getMovieByYearAndTitle(Integer year, String title) {
        return movieRepository.findByYearAndTitle(year, title);
    }

    // R - Query by year
    public List<Movie> getMoviesByYear(Integer year) {
        return movieRepository.findByYear(year);
    }

    // R - Query by director
    public List<Movie> getMoviesByDirector(String director) {
        return movieRepository.findByDirector(director);
    }

    // R - Scan all (Find all)
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // U - Update (原子递增Likes)
    public Optional<Movie> incrementMovieLikes(String id) {
        return movieRepository.findById(id).map(movie -> {
            Integer currentLikes = Optional.ofNullable(movie.getLikes()).orElse(0);
            movie.setLikes(currentLikes + 1);
            return movieRepository.save(movie);
        });
        // 注意：这里是典型的"读-改-写"模式，在并发量极高的场景下可能需要MongoDB的事务或findAndModify操作保证原子性
    }

    // D - Delete
    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }

    // D - Delete all
    public void deleteAllMovies() {
        movieRepository.deleteAll();
    }
}