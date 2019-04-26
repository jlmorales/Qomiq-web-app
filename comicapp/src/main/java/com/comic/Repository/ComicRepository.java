package com.comic.Repository;

import com.comic.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("comicRepository")
public interface ComicRepository extends JpaRepository<Comic, Integer> {

    Comic findById(int id);

    List<Comic> findAllBySeriesId(int id);

    List<Comic> findAllBySeriesIdAndPublicComicTrue(int id);

}
