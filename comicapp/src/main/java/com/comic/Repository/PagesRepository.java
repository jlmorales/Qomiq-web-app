package com.comic.Repository;

import com.comic.model.Pages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagesRepository extends JpaRepository<Pages, Integer> {
    public List<Pages> findAllByComicId(int id);
}
