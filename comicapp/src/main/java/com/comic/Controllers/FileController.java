package com.comic.Controllers;

import com.amazonaws.services.s3.model.S3Object;
import com.comic.Bean.BASE64DecodedMultipartFile;
import com.comic.Service.ComicService;
import com.comic.Service.S3Services;
import com.comic.Service.SeriesService;
import com.comic.Service.UserService;
import com.comic.model.Comic;
import com.comic.model.Series;
import com.comic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    S3Services s3Services;

    @Autowired
    SeriesService seriesService;

    @Autowired
    ComicService comicService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file ,
                             @RequestParam("pngFile") String pngFile,
                             @RequestParam("seriesName") String seriesName,
                             @RequestParam("comicName") String comicName)
                             {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Series series = new Series();
        series.setAuthorUsername(user.getUsername());
        series.setCategory("Action");
        series.setCreationDate(20190425);
        series.setCreationTime(0);
        series.setSeriesViews(0);
        series.setTitle(seriesName);

        List<Series> seriesList = seriesService.findAllSeriesByAuthorUsername(user.getUsername());
        boolean seriesExists= false;
        for(Series s : seriesList){
            if(s.getTitle().equalsIgnoreCase(seriesName)){
                series = s;
                seriesExists = true;
            }
        }
        series = seriesService.saveSeries(series);
        System.out.println(series);
        Comic comic = new Comic();
        comic.setPublicComic(true);
        java.util.Date javaDate = new java.util.Date();
        Date date = new Date(javaDate.getTime());
        comic.setCreationDate(date);
        comic.setComicViews(0);
        //comic.setCreationTime(0);
        comic.setLikes(0);
        comic.setComicTitle(comicName);
        comic.setComicViews(0);
        comic.setSeriesId(series.getId());
        comic = comicService.saveComic(comic);
        System.out.println(comic);
        String keyName = "series"+series.getId()+"comic"+comic.getId()+"."+"json";
        s3Services.uploadFile(keyName, file);
        keyName = "series"+series.getId()+"comic"+comic.getId()+"."+"png";
        System.out.println(pngFile);
        byte[] imagedata = DatatypeConverter.parseBase64Binary(pngFile.substring(pngFile.indexOf(",")+1));
        BASE64DecodedMultipartFile realFile = new BASE64DecodedMultipartFile(imagedata);
        s3Services.uploadFile(keyName, realFile);
        if(!seriesExists){
            keyName = "seriesCover"+series.getId()+"."+"png";
            s3Services.uploadFile(keyName,realFile);
        }
        return "Upload Successfully -> KeyName = " + keyName;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profileImage")
    @ResponseBody
    public String uploadFile(@RequestParam("image") String image){
        byte[] imagedata = DatatypeConverter.parseBase64Binary(image.substring(image.indexOf(",")+1));
        BASE64DecodedMultipartFile realFile = new BASE64DecodedMultipartFile(imagedata);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        s3Services.uploadFile("profileImage" + user.getId(), realFile);
        return "uploaded";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadToGame")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file ,
                             @RequestParam("pngFile") String pngFile,
                             @RequestParam("gameTitle") String gameTitle,
                             @RequestParam("gamePageId") int gamePageId)
    {
        System.out.println(gameTitle);
        System.out.println(gamePageId);
        return "not implemented yet";
    }

}
