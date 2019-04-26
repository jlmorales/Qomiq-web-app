package com.comic.Service;

import com.comic.Repository.ComicRepository;
import com.comic.model.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ComicService")
public class ComicService {

    ComicRepository comicRepository;

    @Autowired
    public ComicService(ComicRepository comicRepository){
        this.comicRepository = comicRepository;

    }

    public Comic findComicById(int id){
        return this.comicRepository.findById(id);
    }

    public List<Comic> findComicsBySeriesId(int id){ return this.comicRepository.findAllBySeriesId(id);}

    public Comic saveComic(Comic comic){ return this.comicRepository.save(comic);}
}
