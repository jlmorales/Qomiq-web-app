package com.comic.Service;

import com.comic.model.series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("seriesService")
public class seriesService {
    private com.comic.Repository.seriesRepository seriesRepository;

    @Autowired
    public seriesService(com.comic.Repository.seriesRepository seriesRepository){
        this.seriesRepository = seriesRepository;
    }

    public List<series> findAllSerieses(){
        return seriesRepository.findAll();
    }

}
