package com.comic.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ComicController {

    @RequestMapping(value = {"/comic/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView category(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contentview");
        return modelAndView;
    }

}
