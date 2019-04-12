package com.comic.Controllers;
import com.comic.Service.SeriesService;
import com.comic.Service.UserService;
import com.comic.model.Series;
import com.comic.model.User;
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
    private SeriesService seriesService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/explore"}, method = RequestMethod.GET)
    public ModelAndView explore() {
        ModelAndView modelAndView = new ModelAndView();
        List<Series> series = seriesService.findAllSeries();
        List<User> users = new ArrayList<>();
        for (Series s: series) {
            User user = userService.findUserByUsername(s.getAuthorUsername());
            users.add(user);
        }
        modelAndView.addObject("series",series);
        modelAndView.addObject("users", users);
        modelAndView.setViewName("explore");
        return modelAndView;
    }

    @RequestMapping(value = {"/explore/{category}"}, method = RequestMethod.GET)
    public ModelAndView category(@PathVariable("category") String category) {
        ModelAndView modelAndView = new ModelAndView();
        List<Series> series = seriesService.findAllSeries();
        List<Series>  filteredList = new ArrayList<>();
        System.out.print(series);
        category = category.toLowerCase();
        System.out.println(category);
        List<User> users = new ArrayList<>();
        for(Series s : series){
            if(s.getCategory().equals(category)){
                filteredList.add(s);
                User user = userService.findUserByUsername(s.getAuthorUsername());
                users.add(user);
            }
        }
        System.out.println(filteredList);
        modelAndView.addObject("series",filteredList);
        modelAndView.addObject("users",users);
        modelAndView.setViewName("explore");
        return modelAndView;
    }


}
