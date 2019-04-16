package com.comic.Controllers;
import ch.qos.logback.core.net.SyslogOutputStream;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
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
                Subscription subscription = subscriptionService.findIfSubscriptionExists(user.getEmail(),currentUser.getEmail());
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

    @RequestMapping(value = {"/explore/subscribe"}, method = RequestMethod.POST)
    public ModelAndView subscribe(@ModelAttribute Series s) {
//        System.out.println(series==null);
        ModelAndView modelAndView = new ModelAndView();
        Subscription newSubscription = new Subscription();
        System.out.println("Series returned to post:");
        System.out.println(s);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        newSubscription.setSubscriberUsername(currentUser.getEmail());
        User author = userService.findUserByUsername(s.getAuthorUsername());
        newSubscription.setSubscribeeUsername(author.getEmail());
        System.out.println(newSubscription);
        subscriptionService.saveSubscription(newSubscription);
        System.out.println(newSubscription);
        modelAndView = new ModelAndView(new RedirectView("/explore"));
        return modelAndView;
    }

}
