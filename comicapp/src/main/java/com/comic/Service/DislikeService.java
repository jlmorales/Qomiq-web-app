package com.comic.Service;

import com.comic.Repository.DislikeRepository;
import com.comic.model.Dislike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dislikeService")
public class DislikeService {
    DislikeRepository dislikeRepository;

    @Autowired
    public DislikeService(DislikeRepository likeRepository) {
        this.dislikeRepository = likeRepository;
    }

    public Dislike findByUsernameandId(String username, int id) {
        return this.dislikeRepository.findByDislikerUsernameAndComicId(username, id);
    }

    public List<Dislike> findByComicId(int id) {
        return this.dislikeRepository.findAllByComicId(id);
    }

    public void saveDislike(Dislike like) {
        this.dislikeRepository.save(like);
    }

    public void deleteDislike(Dislike like) {
        this.dislikeRepository.delete(like);
    }
}
