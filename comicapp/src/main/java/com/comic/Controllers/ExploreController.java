package com.comic.Controllers;
import com.comic.Service.seriesService;
import com.comic.model.series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExploreController {


    @Autowired
    private seriesService seriesService;

    @RequestMapping(value = {"/explore"}, method = RequestMethod.GET)
    public ModelAndView explore() {
        ModelAndView modelAndView = new ModelAndView();
        List<series> serieses = seriesService.findAllSerieses();
        modelAndView.addObject("serieses",serieses);
        modelAndView.setViewName("explore");
        return modelAndView;
    }

    @RequestMapping(value = {"/explore/{category}"}, method = RequestMethod.GET)
    public ModelAndView category(@PathVariable("category") String category) {
        ModelAndView modelAndView = new ModelAndView();
        List<series> serieses = seriesService.findAllSerieses();
        List<series>  filteredList = new ArrayList<>();
        System.out.print(serieses);
        category = category.toLowerCase();
        System.out.println(category);
        for(series series : serieses){
            if(series.getCategory().equals(category)){
                filteredList.add(series);
            }
        }
        System.out.println(filteredList);
        modelAndView.addObject("serieses",filteredList);
        modelAndView.setViewName("explore");
        return modelAndView;
    }


}
