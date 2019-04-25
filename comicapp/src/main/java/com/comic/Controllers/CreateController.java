package com.comic.Controllers;
import com.amazonaws.services.s3.model.S3Object;
import com.comic.Forms.ExploreForm;
import com.comic.Service.S3Services;
import com.comic.Service.SeriesService;
import com.comic.Service.UserService;
import com.comic.model.Series;
import com.comic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@Controller
public class CreateController {

    @Autowired
    UserService userService;

    @Autowired
    SeriesService seriesService;

    @Autowired
    S3Services s3Services;

    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Series> series;
        if (user != null)
            series = seriesService.findAllSeriesByAuthorUsername(user.username);
        else{
            series = null;
        }
        modelAndView.setViewName("editor");
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("series", series);
        return modelAndView;
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.GET)
    public ModelAndView edit(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("thing", "edit1");
        modelAndView.setViewName("editEditor");
        return modelAndView;
    }
}
