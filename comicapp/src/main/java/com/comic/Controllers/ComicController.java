package com.comic.Controllers;

import com.comic.Repository.ComicRepository;
import com.comic.Service.ComicService;
import com.comic.Service.CommentService;
import com.comic.Service.UserService;
import com.comic.model.Comic;
import com.comic.model.Comment;
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
import java.util.LinkedList;
import java.util.List;

@Controller
public class ComicController {

    @Autowired
    private ComicService comicService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/comic/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView category(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        Comic comic = comicService.findComicById(id);
        List<Comment> comments = commentService.findCommentsByComicId(comic.getId());
        List<User> commentators= new ArrayList<>();
        for(Comment comment : comments){
            User user = userService.findUserById(comment.getCommentorId());
            commentators.add(user);
        }
        System.out.println("Comments:");
        System.out.println(comments);
        System.out.println("Comic:");
        System.out.println(comic);
        Comment newComment = new Comment();
        newComment.setComicId(comic.getId());
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("commentators", commentators);
        modelAndView.addObject("newComment", newComment);
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("comic", comic);
        modelAndView.setViewName("contentview");
        return modelAndView;
    }

    @RequestMapping(value = {"/comic/comment"}, method = RequestMethod.POST)
    public ModelAndView comment(@ModelAttribute Comment newComment){
        ModelAndView modelAndView;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        newComment.setCommentorId(currentUser.getId());
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
}
