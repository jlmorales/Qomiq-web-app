package com.comic.Controllers;

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

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private SeriesService seriesServoice;

    @RequestMapping(value = {"/account"}, method = RequestMethod.GET)
    public ModelAndView account() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profilepage");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Series> series = seriesServoice.findAllSeries();
        List<Series> seriesList = new ArrayList<>();
        for (Series s : series) {
            if (s.getAuthorUsername().equals(user.getUsername())) {
                seriesList.add(s);
            }
        }
        modelAndView.addObject("user", user);
        modelAndView.addObject("series", seriesList);
        return modelAndView;
    }
}
