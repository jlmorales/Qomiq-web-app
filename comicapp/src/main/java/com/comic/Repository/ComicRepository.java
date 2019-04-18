package com.comic.Repository;

import com.comic.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("comicRepository")
public interface ComicRepository extends JpaRepository<Comic, Integer> {

    Comic findById(int id);

}
