package com.comic.Controllers;

import com.comic.model.series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.comic.Service.seriesService;

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

}
