package com.comic.Repository;

import com.comic.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Integer> {
        public List<Series> findAll();
}
