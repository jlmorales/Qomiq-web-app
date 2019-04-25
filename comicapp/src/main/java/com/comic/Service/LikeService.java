package com.comic.Service;

import com.comic.Repository.LikeRepository;
import com.comic.model.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("likeService")
public class LikeService {
    LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like findByUsernameandId(String username, int id) {
        return this.likeRepository.findByLikerUsernameAndComicId(username, id);
    }

    public void saveLike(Like like) {
        this.likeRepository.save(like);
    }

    public void deleteLike(Like like) {
        this.likeRepository.delete(like);
    }
}
