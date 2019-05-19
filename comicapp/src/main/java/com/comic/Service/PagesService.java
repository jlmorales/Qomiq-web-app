package com.comic.Service;

import com.comic.Repository.PagesRepository;
import com.comic.model.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pageService")
public class PagesService {

    PagesRepository pagesRepository;

    @Autowired
    public PagesService(PagesRepository likeRepository) {
        this.pagesRepository = likeRepository;
    }

    public List<Pages> findByComicId(int id) {
        return this.pagesRepository.findAllByComicId(id);
    }

    public void savePage(Pages page) { this.pagesRepository.save(page);}
}
