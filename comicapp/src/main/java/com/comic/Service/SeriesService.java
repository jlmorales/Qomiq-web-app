package com.comic.Service;

import com.comic.Repository.SeriesRepository;
import com.comic.model.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SeriesService")
public class SeriesService {
    private SeriesRepository seriesRepository;

    @Autowired
    public SeriesService(SeriesRepository seriesRepository){
        this.seriesRepository = seriesRepository;
    }

    public List<Series> findAllSeries(){
        return seriesRepository.findAll();
    }

    public List<Series> findAllSeriesByAuthorUsername(String authorUsername) { return seriesRepository.findAllByAuthorUsername(authorUsername);}

}
