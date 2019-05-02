package com.comic.Controllers;

import com.comic.Service.SeriesService;
import com.comic.Service.SubscriptionService;
import com.comic.Service.UserService;
import com.comic.model.Series;
import com.comic.model.Subscription;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private SubscriptionService subscriptionService;

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
        Subscription subscription = null;
        if (currentUser != null) {
            subscription = subscriptionService.findIfSubscriptionExists(profileUser.getUsername(), currentUser.getUsername());
        }
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("profileUser", profileUser);
        modelAndView.addObject("series", seriesList);
        modelAndView.addObject("subscription", subscription);
        return modelAndView;
    }

    @RequestMapping(value = {"/profile/subscribe/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView subscribe(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Subscription newSubscription = new Subscription();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        if (currentUser != null) {
            newSubscription.setSubscriberUsername(currentUser.getUsername());
            User author = userService.findUserById(id);
            newSubscription.setSubscribeeUsername(author.getUsername());
            subscriptionService.saveSubscription(newSubscription);
        }
        modelAndView = new ModelAndView(new RedirectView("/profile/" + id));
        return modelAndView;
    }

    @RequestMapping(value = {"/profile/unsubscribe/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView unsubscribe( @PathVariable("id") int id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User author = userService.findUserById(id);
        User currentUser = userService.findUserByEmail(auth.getName());
        Subscription subscription = subscriptionService.findIfSubscriptionExists(author.getUsername(),currentUser.getUsername());
        subscriptionService.deleteSubscription(subscription);
        ModelAndView modelAndView;
        modelAndView = new ModelAndView(new RedirectView("/profile/" + id));
        return modelAndView;

    }

}