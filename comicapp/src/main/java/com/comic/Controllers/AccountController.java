package com.comic.Controllers;

import com.comic.Service.ComicService;
import com.comic.Service.SeriesService;
import com.comic.Service.SubscriptionService;
import com.comic.Service.UserService;
import com.comic.model.Comic;
import com.comic.model.Series;
import com.comic.model.Subscription;
import com.comic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ComicService comicService;

    @RequestMapping(value = {"/account"}, method = RequestMethod.GET)
    public ModelAndView account() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profilepage");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Series> series = seriesService.findAllSeries();
        List<Series> seriesList = new ArrayList<>();
        for (Series s : series) {
            if (s.getAuthorUsername().equals(user.getUsername())) {
                seriesList.add(s);
            }
        }
        List<Subscription> subscribers = subscriptionService.findBySubscriberUsername(user.getUsername());
        System.out.println(subscribers);
        List<User> subscriptions = new ArrayList<>();
        for (Subscription s : subscribers) {
            User u = userService.findUserByUsername(s.getSubscribeeUsername());
            subscriptions.add(u);
        }
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("series", seriesList);
        System.out.println("Subscriptions: " + subscriptions);
        modelAndView.addObject("subscriptions", subscriptions);
        modelAndView.addObject("currentUser", currentUser);
        return modelAndView;
    }

    @RequestMapping(value = {"/account/series/{id:[\\d]+}"},method = RequestMethod.GET)
    public ModelAndView manageComics(@PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView();
        Series series = seriesService.findSeriesById(id);
        if(series == null){
            modelAndView = new ModelAndView(new RedirectView("/account" ));
            return modelAndView;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println(!series.getAuthorUsername().equalsIgnoreCase(user.getUsername()));
        if(!series.getAuthorUsername().equalsIgnoreCase(user.getUsername())){
            modelAndView = new ModelAndView(new RedirectView("/account" ));
            return modelAndView;
        }
        List<Comic> comics = comicService.findComicsBySeriesId(series.getId());
        System.out.println(series);
        System.out.println(comics);
        System.out.println(user);
        modelAndView.addObject("currentUser",user);
        modelAndView.addObject("series",series);
        modelAndView.addObject("comics",comics);
        modelAndView.setViewName("manageComics");
        return  modelAndView;

    }

    @RequestMapping(value = {"/account/series/makepublic"}, method = RequestMethod.POST)
    public ModelAndView makePublic(@ModelAttribute Comic comic){
        ModelAndView modelAndView;
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = userService.findUserByEmail(auth.getName());
        comic = comicService.findComicById(comic.getId());
        comic.setPublicComic(true);
        comicService.saveComic(comic);
        Series series =seriesService.findSeriesById(comic.getSeriesId());
        modelAndView = new ModelAndView(new RedirectView("/account/series/" + series.getId()));
        return modelAndView;
    }

    @RequestMapping(value = {"/account/series/makeprivate"}, method = RequestMethod.POST)
    public ModelAndView makePrivate(@ModelAttribute Comic comic){
        ModelAndView modelAndView;
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = userService.findUserByEmail(auth.getName());
        comic = comicService.findComicById(comic.getId());
        comic.setPublicComic(false);
        comicService.saveComic(comic);
        Series series =seriesService.findSeriesById(comic.getSeriesId());
        modelAndView = new ModelAndView(new RedirectView("/account/series/" + series.getId()));
        return modelAndView;
    }


    @RequestMapping(value = {"/profileSettings"},method = RequestMethod.GET)
    public ModelAndView profileSettings() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profileSettings");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", currentUser);
        return modelAndView;
    }
}
