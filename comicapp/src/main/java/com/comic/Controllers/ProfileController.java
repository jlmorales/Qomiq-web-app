package com.comic.Controllers;

import com.comic.Service.SeriesService;
import com.comic.Service.UserService;
import com.comic.model.Series;
import com.comic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private SeriesService seriesService;

    @RequestMapping(value = {"/profile/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView profile(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("otherprofile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        User profileUser = userService.findUserById(id);
        if(currentUser != null && currentUser.getId() == profileUser.getId()){
            modelAndView = new ModelAndView(new RedirectView("/account"));
            return modelAndView;
        }
        List<Series> seriesList = seriesService.findAllSeriesByAuthorUsername(profileUser.getUsername());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("profileUser", profileUser);
        modelAndView.addObject("series", seriesList);
        return modelAndView;
    }

}