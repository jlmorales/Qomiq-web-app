package com.comic.Repository;

import com.comic.model.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("dislikeRepository")
public interface DislikeRepository extends JpaRepository<Dislike, Integer> {

        Dislike findByDislikerUsernameAndComicId(String username, int id);
}
