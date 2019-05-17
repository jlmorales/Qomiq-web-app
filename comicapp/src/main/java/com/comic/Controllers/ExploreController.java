package com.comic.Controllers;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.comic.Comparators.SeriesDateComparator;
import com.comic.Forms.ExploreForm;
import com.comic.Forms.SubscribeForm;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ExploreController {


    @Autowired
    private SeriesService seriesService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @RequestMapping(value = {"/explore"}, method = RequestMethod.GET)
    public ModelAndView explore() {
        ModelAndView modelAndView = new ModelAndView();
        List<Series> series = seriesService.findAllSeries();
        List<User> users = new ArrayList<>();
        List<Boolean> isSubscribed = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        for (Series s: series) {
            User user = userService.findUserByUsername(s.getAuthorUsername());
            users.add(user);
            if(currentUser != null){
                Subscription subscription = subscriptionService.findIfSubscriptionExists(user.getUsername(),currentUser.getUsername());
                if(subscription == null){
                    isSubscribed.add(false);
                }
                else{
                    isSubscribed.add(true);
                }
            }

        }

        modelAndView.addObject("currentUser", currentUser);
//        SubscribeForm form = new SubscribeForm("");
//        modelAndView.addObject("subscribeForm", form);
//        modelAndView.addObject("subscribeForms", forms);
        System.out.println("SERIES BEFORE SERVING EXPLORE" + series);
        ExploreForm exploreForm = new ExploreForm();
        modelAndView.addObject("exploreForm", exploreForm);
        modelAndView.addObject("series",series);
        modelAndView.addObject("users", users);
        modelAndView.addObject("isSubscribed", isSubscribed);
        modelAndView.setViewName("explore");
        return modelAndView;
    }

    @RequestMapping(value = {"/explore/{category}"}, method = RequestMethod.GET)
    public ModelAndView category(@PathVariable("category") String category) {
        ModelAndView modelAndView = new ModelAndView();
        List<Series> series = seriesService.findAllSeries();
        List<Series>  filteredList = new ArrayList<>();

        category = category.toLowerCase();
        List<User> users = new ArrayList<>();
        for(Series s : series){
            if(s.getCategory().equals(category)){
                filteredList.add(s);
                User user = userService.findUserByUsername(s.getAuthorUsername());
                users.add(user);
            }
        }
        modelAndView.addObject("series",filteredList);
        modelAndView.addObject("users",users);
        modelAndView.setViewName("explore");
        return modelAndView;
    }

    @RequestMapping(value = "/explore/searchBy", method = RequestMethod.GET)
    public ModelAndView searchBy(@RequestParam(value = "category", required = false) String category,
                              @RequestParam(value = "sortBy", required = false) String sortOrder){
        System.out.println(category);
        System.out.println((sortOrder));
        ExploreForm exploreForm = new ExploreForm();
        ModelAndView modelAndView = new ModelAndView();
        List<Series> series;
        List<User> users = new ArrayList<>();
        List<Boolean> isSubscribed = new ArrayList<>();
        List<Series>  filteredList = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        //if params are null then set to default values
        if(sortOrder == null){
            sortOrder = "none";
        }
        if(category == null){
            category = "All";
        }

        //if sortOrder is views then find all series by views
        //else return all series
        if(sortOrder.equalsIgnoreCase("views")){
            series = seriesService.findAllSeriesByViews();
        }
        else if (sortOrder.equalsIgnoreCase("date")){
            series = seriesService.findAllSeriesByDateDesc();
//            Collections.sort(series ,new SeriesDateComparator());
//            Collections.reverse(series);

        }
        else{
            series = seriesService.findAllSeries();
        }
        //if category is not All set exploreForm to "Category"
        if(!category.equalsIgnoreCase("all")){
            exploreForm.setCategory(category);
        }
        //if category is not all then find series by category
        //else find all series
        //also add authors to list of users
        if(!category.equalsIgnoreCase("All")){
            for(Series s : series){
                if(s.getCategory().equalsIgnoreCase(category)){
                    filteredList.add(s);
                    User user = userService.findUserByUsername(s.getAuthorUsername());
                    users.add(user);
                }
            }
        }
        else{
            for(Series s : series){
                    filteredList.add(s);
                    User user = userService.findUserByUsername(s.getAuthorUsername());
                    users.add(user);
            }
        }
        //find all subscriptions if they exist for current user and corresponding author of series in filtered list
        //add subscriptions to isSubscribed list
        for (Series s: filteredList) {
            User user = userService.findUserByUsername(s.getAuthorUsername());
            if(currentUser != null){
                Subscription subscription = subscriptionService.findIfSubscriptionExists(user.getUsername(),currentUser.getUsername());
                if(subscription == null){
                    isSubscribed.add(false);
                }
                else{
                    isSubscribed.add(true);
                }
            }
        }
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("exploreForm", exploreForm);
        modelAndView.addObject("series",filteredList);
        modelAndView.addObject("users", users);
        modelAndView.addObject("isSubscribed", isSubscribed);
        modelAndView.setViewName("explore");
        return modelAndView;
    }

    @RequestMapping(value = {"/explore/subscribe"}, method = RequestMethod.POST)
    public ModelAndView subscribe(@ModelAttribute Series s) {
//        System.out.println(series==null);
        ModelAndView modelAndView = new ModelAndView();
        Subscription newSubscription = new Subscription();
        System.out.println("Series returned to post:");
        System.out.println(s);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        newSubscription.setSubscriberUsername(currentUser.getUsername());
        User author = userService.findUserByUsername(s.getAuthorUsername());
        newSubscription.setSubscribeeUsername(author.getUsername());
        System.out.println(newSubscription);
        subscriptionService.saveSubscription(newSubscription);
        System.out.println(newSubscription);
        modelAndView = new ModelAndView(new RedirectView("/explore"));
        return modelAndView;
    }

    @RequestMapping(value = {"/explore/unsubscribe"}, method = RequestMethod.POST)
    public ModelAndView unsubscribe(@ModelAttribute Series s){
        System.out.println("From inside of unsubscribe");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User author = userService.findUserByUsername(s.getAuthorUsername());
        User currentUser = userService.findUserByEmail(auth.getName());
        Subscription subscription = subscriptionService.findIfSubscriptionExists(author.getUsername(),currentUser.getUsername());
        System.out.println(subscription.getSubscriptionId());
        subscriptionService.deleteSubscription(subscription);
        ModelAndView modelAndView;
        modelAndView = new ModelAndView(new RedirectView("/explore"));
        return modelAndView;

    }

}
