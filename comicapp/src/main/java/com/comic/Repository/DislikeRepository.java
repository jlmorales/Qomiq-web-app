package com.comic.Repository;

import com.comic.model.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dislikeRepository")
public interface DislikeRepository extends JpaRepository<Dislike, Integer> {

        List<Dislike> findAllByComicId(int id);

        Dislike findByDislikerUsernameAndComicId(String username, int id);
}
