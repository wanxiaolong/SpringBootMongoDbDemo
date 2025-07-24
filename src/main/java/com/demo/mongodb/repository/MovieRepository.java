package com.demo.mongodb.repository;

import com.demo.mongodb.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    Optional<Movie> findByYearAndTitle(Integer year, String title);

    List<Movie> findByYear(Integer year);

    List<Movie> findByDirector(String director);
}