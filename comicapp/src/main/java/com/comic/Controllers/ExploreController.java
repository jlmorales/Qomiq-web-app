package com.comic.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExploreController {


        @RequestMapping(value = {"/explore"}, method = RequestMethod.GET)
        public ModelAndView explore() {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("explore");
            return modelAndView;
        }

}
