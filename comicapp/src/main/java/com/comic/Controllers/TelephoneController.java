package com.comic.Controllers;

import com.comic.Service.*;
import com.comic.model.*;
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

import java.util.LinkedList;
import java.util.List;

@Controller
public class TelephoneController {

    @Autowired
    public UserService userService;

    @Autowired
    public GameService gameService;

    @Autowired
    public GamePageService gamePageService;

    @Autowired
    public SubscriptionService subscriptionService;

    @Autowired
    public GamePlayerService gamePlayerService;

    @RequestMapping(value = {"/play"}, method = RequestMethod.GET)
    public ModelAndView play() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("games", gameService.findAllGamesByUserId(user.getId()));
        modelAndView.addObject("newGame",new Game());
        modelAndView.addObject("currentUser", user);
        modelAndView.setViewName("telephonegame");
        return modelAndView;
    }

    @RequestMapping(value = {"/game/new"}, method = RequestMethod.POST)
    public ModelAndView newGame(@ModelAttribute Game game) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        game = new Game();
        game.setGameName("default name");
        game.setUserId(user.getId());
        game = gameService.saveGame(game);
        List<Subscription> subscriptions = subscriptionService.findBySubscribeeUsername(user.getUsername());
        for(Subscription s : subscriptions){
            GamePlayer gamePlayer = new GamePlayer();
            User subscriber = userService.findUserByUsername(s.getSubscriberUsername());
            gamePlayer.setGameId(game.getId());
            gamePlayer.setUserId(subscriber.getId());
            gamePlayerService.savePlayer(gamePlayer);
        }
        modelAndView = new ModelAndView(new RedirectView("/game/"+game.getId()));
        return modelAndView;
    }

    @RequestMapping(value = "game/{id:[\\d]+}" , method = RequestMethod.GET)
    public ModelAndView game(@PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Game game = gameService.findGameById(id);
        List<GamePage> gamePages = gamePageService.findGamePagesByGameId(id);
        if(gamePages.size() < 1){
            modelAndView.addObject("firstPage", true);
        }
        List<GamePlayer> players = gamePlayerService.findGamePlayersByGameId(game.getId());
        List<User> users = new LinkedList<>();
        for(GamePlayer player: players){
            users.add(userService.findUserById(player.getUserId()));
        }
        modelAndView.addObject("users", users);
        modelAndView.addObject("players",players );
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("game", game);
        modelAndView.setViewName("gameMenu");
        return modelAndView;
    }

}
