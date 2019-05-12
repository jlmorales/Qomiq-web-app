package com.comic.Controllers;

import com.comic.Service.*;
import com.comic.model.*;
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
public class ComicController {

    @Autowired
    private ComicService comicService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private DislikeService dislikeService;

    @RequestMapping(value = {"/comic/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView category(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        Comic comic = comicService.findComicById(id);
        if(!comic.isPublicComic()){
            modelAndView = new ModelAndView(new RedirectView("/explore"));
            return modelAndView;
        }
        List<Comment> comments = commentService.findCommentsByComicId(comic.getId());
        List<User> commentators= new ArrayList<>();
        for(Comment comment : comments){
            User user = userService.findUserById(comment.getCommenterId());
            commentators.add(user);
        }
        System.out.println("Comments:");
        System.out.println(comments);
        System.out.println("Comic:");
        System.out.println(comic);
        comic.setComicViews(comic.getComicViews() + 1);
        comicService.saveComic(comic);
        Comment newComment = new Comment();
        newComment.setComicId(comic.getId());
        Like like = null;
        Dislike dislike = null;
        if (currentUser != null) like = likeService.findByUsernameandId(currentUser.getUsername(), id);
        if (currentUser != null) dislike = dislikeService.findByUsernameandId(currentUser.getUsername(), id);
        Series series = seriesService.findSeriesById(comic.getSeriesId());
        series.setSeriesViews(series.getSeriesViews() + 1);
        seriesService.saveSeries(series);
        User profileUser = userService.findUserByUsername(series.getAuthorUsername());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("commentators", commentators);
        modelAndView.addObject("newComment", newComment);
        modelAndView.addObject("profileUser", profileUser);
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("comic", comic);
        modelAndView.addObject("series", series);
        modelAndView.addObject("like", like);
        modelAndView.addObject("dislike", dislike);
        modelAndView.setViewName("contentview");
        return modelAndView;
    }

    @RequestMapping(value = {"/comic/comment"}, method = RequestMethod.POST)
    public ModelAndView comment(@ModelAttribute Comment newComment){
        ModelAndView modelAndView;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        newComment.setCommenterId(currentUser.getId());
        System.out.println(newComment);
        commentService.saveComment(newComment);
        modelAndView = new ModelAndView(new RedirectView("/comic/" + newComment.getComicId()));
        return modelAndView;
    }

    @RequestMapping(value = {"/comic/deleteComment"}, method = RequestMethod.POST)
    public ModelAndView deleteComment(@ModelAttribute Comment comment){
        ModelAndView modelAndView;
        System.out.println(comment);
        comment = commentService.findCommentByCommentId(comment.getCommentId());
        System.out.println(comment);
        commentService.deleteComment(comment);
        modelAndView = new ModelAndView(new RedirectView("/comic/" + comment.getComicId()));
        return modelAndView;
    }

    @RequestMapping(value = {"/comic/like/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView like(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Like newLike = new Like();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        Like like = likeService.findByUsernameandId(currentUser.getUsername(), id);
        Dislike dislike = dislikeService.findByUsernameandId(currentUser.getUsername(), id);
        Comic comic = comicService.findComicById(id);
        if (like == null) {
            newLike.setLikerUsername(currentUser.getUsername());
            newLike.setComicId(id);
            likeService.saveLike(newLike);
            comic.setLikes(comic.getLikes() + 1);
        }
        else {
            likeService.deleteLike(like);
            comic.setLikes(comic.getLikes() - 1);
        }
        if (dislike != null) {
            dislikeService.deleteDislike(dislike);
            comic.setDislikes(comic.getDislikes() - 1);
        }
        comicService.saveComic(comic);
        modelAndView = new ModelAndView(new RedirectView("/comic/" + id));
        return modelAndView;
    }

    @RequestMapping(value = {"/comic/dislike/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView dislike(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Dislike newDislike = new Dislike();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        Like like = likeService.findByUsernameandId(currentUser.getUsername(), id);
        Dislike dislike = dislikeService.findByUsernameandId(currentUser.getUsername(), id);
        Comic comic = comicService.findComicById(id);
        if (dislike == null) {
            newDislike.setDislikerUsername(currentUser.getUsername());
            newDislike.setComicId(id);
            dislikeService.saveDislike(newDislike);
            comic.setDislikes(comic.getDislikes() + 1);
        }
        else {
            dislikeService.deleteDislike(dislike);
            comic.setDislikes(comic.getDislikes() - 1);
        }
        if (like != null) {
            likeService.deleteLike(like);
            comic.setLikes(comic.getLikes() - 1);
        }
        comicService.saveComic(comic);
        modelAndView = new ModelAndView(new RedirectView("/comic/" + id));
        return modelAndView;
    }

    @RequestMapping(value = {"/comic/next/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView next(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Comic comic = comicService.findComicById(id);
        List<Comic> comics = comicService.findComicsBySeriesIdAndPublicComic(comic.getSeriesId());
        if (comics.size() > comics.indexOf(comic)+1) {
            Comic nextComic = comics.get(comics.indexOf(comic) + 1);
            int nextId = nextComic.getId();
            modelAndView = new ModelAndView(new RedirectView("/comic/" + nextId));
        }
        else {
            modelAndView = new ModelAndView(new RedirectView("/comic/" + id));
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/comic/prev/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView prev(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Comic comic = comicService.findComicById(id);
        List<Comic> comics = comicService.findComicsBySeriesIdAndPublicComic(comic.getSeriesId());
        if ((comics.indexOf(comic)-1) >= 0) {
            Comic prevComic = comics.get(comics.indexOf(comic) - 1);
            int prevId = prevComic.getId();
            modelAndView = new ModelAndView(new RedirectView("/comic/" + prevId));
        }
        else {
            modelAndView = new ModelAndView(new RedirectView("/comic/" + id));
        }
        return modelAndView;
    }

}
