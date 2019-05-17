package com.comic.Repository;

import com.comic.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("likeRepository")
public interface LikeRepository extends JpaRepository<Like, Integer> {

    List<Like> findAllByComicId(int id);

    Like findByLikerUsernameAndComicId(String username, int id);
}
