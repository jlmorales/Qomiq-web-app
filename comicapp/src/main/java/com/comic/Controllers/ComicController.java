package com.comic.Controllers;

import com.comic.Repository.ComicRepository;
import com.comic.Service.ComicService;
import com.comic.model.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ComicController {

    @Autowired
    private ComicService comicService;

    @RequestMapping(value = {"/comic/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView category(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Comic comic = comicService.findComicById(id);
        System.out.println(comic);
        modelAndView.addObject("comic", comic);
        modelAndView.setViewName("contentview");
        return modelAndView;
    }

}
