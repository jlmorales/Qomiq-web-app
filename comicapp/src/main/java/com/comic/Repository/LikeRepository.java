package com.comic.Repository;

import com.comic.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("likeRepository")
public interface LikeRepository extends JpaRepository<Like, Integer> {

    Like findByLikerUsernameAndComicId(String username, int id);
}
