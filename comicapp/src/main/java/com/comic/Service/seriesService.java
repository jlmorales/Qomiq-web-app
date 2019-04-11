package com.comic.Service;

import com.comic.Repository.seriesRepository;
import com.comic.model.series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("seriesService")
public class seriesService {
    private seriesRepository seriesRepository;

    @Autowired
    public seriesService(seriesRepository seriesRepository){
        this.seriesRepository = seriesRepository;
    }

    public List<series> findAllSerieses(){
        return seriesRepository.findAll();
    }

}
