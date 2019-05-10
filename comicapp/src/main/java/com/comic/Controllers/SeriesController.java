package com.comic.Controllers;

import com.comic.Service.ComicService;
import com.comic.Service.SeriesService;
import com.comic.Service.UserService;
import com.comic.model.Comic;
import com.comic.Controllers.FileController;
import com.comic.model.Series;
import com.comic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class SeriesController {

    @Autowired
    SeriesService seriesService;

    @Autowired
    ComicService comicService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/series/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView series(@PathVariable("id") int id){
        ModelAndView modelAndView;
        Series series = seriesService.findSeriesById(id);
        List<Comic> comics = comicService.findComicsBySeriesIdAndPublicComic(id);
        modelAndView = new ModelAndView();
        modelAndView.addObject("series", series);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("comics", comics);
        modelAndView.setViewName("series");
        return modelAndView;
    }

}
