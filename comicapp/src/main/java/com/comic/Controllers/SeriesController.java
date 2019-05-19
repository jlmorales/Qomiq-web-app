package com.comic.Controllers;

import com.comic.Service.*;
import com.comic.model.*;
import com.comic.Controllers.FileController;
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

import java.util.List;

@Controller
public class SeriesController {

    @Autowired
    private S3Services s3Services;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private ComicService comicService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private DislikeService dislikeService;

    @RequestMapping(value = {"/series/{id:[\\d]+}"}, method = RequestMethod.GET)
    public ModelAndView series(@PathVariable("id") int id){
        ModelAndView modelAndView;
        Series series = seriesService.findSeriesById(id);
        List<Comic> comics = comicService.findComicsBySeriesIdAndPublicComic(id);
        Series seriesName = seriesService.findSeriesById(id);
        modelAndView = new ModelAndView();
        modelAndView.addObject("series", series);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("comics", comics);
        modelAndView.addObject("series", seriesName);
        modelAndView.setViewName("series");
        return modelAndView;
    }

    @RequestMapping(value="/series/delete/")
    public ModelAndView delete(@ModelAttribute Series series) {
        List<Comic> comics = comicService.findComicsBySeriesId(series.getId());
        for (Comic comic : comics) {
            List<Comment> comments = commentService.findCommentsByComicId(comic.getId());
            for (Comment comment : comments) {
                commentService.deleteComment(comment);
            }
            List<Like> likes = likeService.findByComicId(comic.getId());
            for (Like like : likes) {
                likeService.deleteLike(like);
            }
            List<Dislike> dislikes = dislikeService.findByComicId(comic.getId());
            for (Dislike dislike : dislikes) {
                dislikeService.deleteDislike(dislike);
            }
            comicService.deleteComic(comic);
            s3Services.deleteFileFromS3Bucket("series" + series.getId() + "comic" + comic.getId() + ".png");
            s3Services.deleteFileFromS3Bucket("series" + series.getId() + "comic" + comic.getId() + ".json");
        }
        seriesService.deleteSeries(series);
        s3Services.deleteFileFromS3Bucket("seriesCover" + series.getId() + ".png");
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/account"));
        return modelAndView;
    }
}
