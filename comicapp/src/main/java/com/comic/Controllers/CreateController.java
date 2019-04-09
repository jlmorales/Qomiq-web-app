package com.comic.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreateController {

    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editor");
        return modelAndView;
    }
}
