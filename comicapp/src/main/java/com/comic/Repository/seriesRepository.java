package com.comic.Repository;

import com.comic.model.series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface seriesRepository extends JpaRepository<series, Integer> {
        public List<series> findAll();
}
