package com.comic.Controllers;
import com.amazonaws.services.apigateway.model.Model;
import com.comic.Service.S3Services;
import com.comic.Service.SeriesService;
import com.comic.Service.UserService;
import com.comic.model.Series;
import com.comic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CreateController {


    @Autowired
    UserService userService;

    @Autowired
    SeriesService seriesService;

    @Autowired
    S3Services s3Services;

    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Series> series = seriesService.findAllSeriesByAuthorUsername(user.username);
        modelAndView.setViewName("editor");
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("series", series);
        return modelAndView;
    }



    public void upload(String bucketName, String fileName, String filePath) {
        s3Services.uploadFile(bucketName, fileName, filePath);
    }

    public void delete(String fileName, String bucketName){
        s3Services.deleteFileFromS3Bucket(fileName,bucketName);
    }

}
